package me.javigs82.startwars.domain;

import me.javigs82.startwars.domain.model.ModelResult;
import me.javigs82.startwars.domain.model.People;
import me.javigs82.startwars.domain.model.StartShip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Service
public class PeopleServiceImpl implements PeopleService {

    private static final Logger log = LoggerFactory.getLogger(PeopleServiceImpl.class);

    @Autowired
    @Qualifier("peopleAdapter")
    private PeoplePort peopleAdapter;

    @Autowired
    @Qualifier("startShipAdapter")
    private StartShipPort startShipAdapter;

    @Override
    public Optional<List<People>> getPeopleSortedByNameASC() throws ExecutionException, InterruptedException {
        return Optional.of(getPeopleSortedByName(SortDirection.ASC));
    }

    @Override
    public Optional<List<People>> getPeopleSortedByNameDESC() throws ExecutionException, InterruptedException {
        return Optional.of(getPeopleSortedByName(SortDirection.DESC));
    }

    @Override
    public Optional<List<People>> getPeopleSortedByCreatedASC() throws ExecutionException, InterruptedException {
        return Optional.of(getPeopleSortedByCreated(SortDirection.ASC));
    }

    @Override
    public Optional<List<People>> getPeopleSortedByCreatedDESC() throws ExecutionException, InterruptedException {
        return Optional.of(getPeopleSortedByCreated(SortDirection.DESC));
    }

    private List<People> getPeopleSortedByName(SortDirection sortDirection) throws InterruptedException, ExecutionException {
        log.debug("getPeopleSortedByName {}", sortDirection);
        //Sorting using comparator: bu default ASC
        Comparator<People> comparatorPeople;
        switch (sortDirection) {
            //comparator DESC
            case DESC:
                comparatorPeople = (p1, p2) -> p2.getName().compareTo(p1.getName());
                break;
            //Comparator ASC & default
            default:
                comparatorPeople = (p1, p2) -> p1.getName().compareTo(p2.getName());
                break;
        }

        List<People> people = getAll();
        people.sort(comparatorPeople);
        return people;
    }

    private List<People> getPeopleSortedByCreated(SortDirection sortDirection) throws InterruptedException, ExecutionException {
        log.debug("getPeopleSortedByCreated {}", sortDirection);
        //Sorting using comparator: bu default ASC
        Comparator<People> comparatorPeople;
        switch (sortDirection) {
            //comparator DESC
            case DESC:
                comparatorPeople = (p1, p2) ->
                        LocalDateTime.parse(p2.getCreated(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                                .compareTo(LocalDateTime.parse(p1.getCreated(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                break;
            //Comparator ASC & default
            default:
                comparatorPeople = (p1, p2) ->
                        LocalDateTime.parse(p1.getCreated(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                                .compareTo(LocalDateTime.parse(p2.getCreated(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                break;
        }

        List<People> people = getAll();
        people.sort(comparatorPeople);
        return people;

    }

    private List<People> getAll() throws InterruptedException, ExecutionException {
        //First Result to calculate numberOfThreads to make requests in a async way
        ModelResult<People> firstResult = peopleAdapter.getPeopleByPage(1).get();

        List<People> result = new ArrayList<>(firstResult.getResults());

        final int resultCount = firstResult.getCount();
        //supposing resultPeople has the same results size for all request, except the last one
        final int numberOfRegPerRequest = firstResult.getResults().size();
        final int numberOfThreads = (resultCount / numberOfRegPerRequest) + 1;

        //starting in page 2 as page 1 has been already retrieved
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 2; i <= numberOfThreads; i++) {
            pageNumbers.add(i);
        }


        List<CompletableFuture<ModelResult<People>>> peopleResultFutures = pageNumbers.stream()
                .map(page -> peopleAdapter.getPeopleByPage(page))
                .collect(Collectors.toList());

        // Create a combined Future using allOf()
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                peopleResultFutures.toArray(new CompletableFuture[0])
        );

        // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -
        CompletableFuture<List<ModelResult<People>>> allPeopleResultFuture = allFutures.thenApply(v -> peopleResultFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));

        List<People> people = allPeopleResultFuture.thenApply(peopleResults -> peopleResults.stream()
                .flatMap(peopleResult -> peopleResult.getResults().stream())
                .collect(Collectors.toList()))
                .join();

        people.parallelStream().forEach(
                p -> p.setStarships(getStartShipsForPeople(p))
        );

        result.addAll(people);

        return result;
    }

    private List<StartShip> getStartShipsForPeople(People p) {

        List<CompletableFuture<StartShip>> startShipFutures = Arrays.stream(p.getStarshipsUrl())
                .map(url -> startShipAdapter.getStartShipByUrl(url))
                .collect(Collectors.toList());

        // Create a combined Future using allOf()
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                startShipFutures.toArray(new CompletableFuture[0])
        );

        // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -
        CompletableFuture<List<StartShip>> allStartShipsFuture = allFutures.thenApply(v -> startShipFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));

        List<StartShip> startShips = allStartShipsFuture.thenApply(startShipResults -> startShipResults.stream()
                .collect(Collectors.toList()))
                .join();

        return startShips;

    }


}
