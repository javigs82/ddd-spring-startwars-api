package me.javigs82.startwars.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


@Service
public class PeopleServiceImpl implements PeopleService {

    private static final Logger log = LoggerFactory.getLogger(PeopleServiceImpl.class);

    @Autowired
    @Qualifier("peopleAdapter")
    private PeoplePort peopleAdapter;

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

        List<People> people = peopleAdapter.getAll();
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

        List<People> people = peopleAdapter.getAll();
        people.sort(comparatorPeople);
        return people;

    }


}
