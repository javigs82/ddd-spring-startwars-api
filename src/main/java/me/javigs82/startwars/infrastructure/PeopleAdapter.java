package me.javigs82.startwars.infrastructure;

import me.javigs82.startwars.domain.People;
import me.javigs82.startwars.domain.PeoplePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Component("peopleAdapter")
public class PeopleAdapter implements PeoplePort {

    private static Logger log = LoggerFactory.getLogger(PeopleAdapter.class);

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<People> getAll() throws InterruptedException, ExecutionException {
        List<People> result = new ArrayList<>();
        //Init result to calculate numberOfThreads to make requests in a async way
        PeopleResult initResult = this.getPeopleByPage(1).get();
        result.addAll(initResult.results);
        final int resultCount = initResult.count;
        final int numberOfRegPerRequest = 10;
        final int numberOfThreads = (resultCount / numberOfRegPerRequest) + 1;

        //starting in page 2 as page 1 has been already retrieved
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 2; i <= numberOfThreads; i++) {
            pageNumbers.add(i);
        }


        List<CompletableFuture<PeopleResult>> peopleResultFutures = pageNumbers.stream()
                .map(page -> getPeopleByPage(page))
                .collect(Collectors.toList());


        // Create a combined Future using allOf()
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                peopleResultFutures.toArray(new CompletableFuture[peopleResultFutures.size()])
        );

        // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -
        CompletableFuture<List<PeopleResult>> allPeopleResultFuture = allFutures.thenApply(v -> {
            return peopleResultFutures.stream()
                    .map(peopleResultFuture -> peopleResultFuture.join())
                    .collect(Collectors.toList());
        });

        CompletableFuture<List<People>> people = allPeopleResultFuture.thenApply(peopleResults -> {
             return peopleResults.stream()
                    //.filter (peopleResult -> !peopleResult.results.isEmpty())
                     //.count();
            .flatMap (peopleResult -> peopleResult.getResults().stream())
                     .collect(Collectors.toList());
        });

        result.addAll(people.get());

        return result;
    }

    @Async("asyncExecutor")
    private CompletableFuture<PeopleResult> getPeopleByPage(int page) {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("getPeopleByPage {}", page);

            //Set the headers you need send, like User-Agent to avoid 403
            final HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/72.0.3626.119 Safari/537.36");
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            final HttpEntity<PeopleResult> httpEntity = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://swapi.co/api/people/")
                    .queryParam("format", "json")
                    .queryParam("page", page);

            final ResponseEntity<PeopleResult> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    httpEntity,
                    PeopleResult.class);
            log.trace("peopleResult, {}", response.getBody());
            log.debug("getPeopleByPage completed");

            return response.getBody();
        });
    }

    /**
     * Static inner class to retrieve people result
     */
    static class PeopleResult {

        int count;
        String next;
        String previous;
        List<People> results;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public List<People> getResults() {
            return results;
        }

        public void setResults(List<People> results) {
            this.results = results;
        }
    }

}
