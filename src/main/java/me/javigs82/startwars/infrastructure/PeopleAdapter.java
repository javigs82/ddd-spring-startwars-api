package me.javigs82.startwars.infrastructure;

import me.javigs82.startwars.domain.PeoplePort;
import me.javigs82.startwars.domain.model.ModelResult;
import me.javigs82.startwars.domain.model.People;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.CompletableFuture;


@Component("peopleAdapter")
public class PeopleAdapter implements PeoplePort {

    private static final Logger log = LoggerFactory.getLogger(PeopleAdapter.class);

    @Autowired
    private RestTemplate restTemplate;


    @Override
    @Async("asyncExecutor")
    public CompletableFuture<ModelResult<People>> getPeopleByPage(int page) {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("getPeopleByPage {}", page);

            //Set the headers you need send, like User-Agent to avoid 403
            final HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/72.0.3626.119 Safari/537.36");
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            final HttpEntity<ModelResult<People>> httpEntity = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://swapi.co/api/people/")
                    .queryParam("format", "json")
                    .queryParam("page", page);

            final ResponseEntity<ModelResult<People>> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    httpEntity, new ParameterizedTypeReference<ModelResult<People>>() {
                    });
            log.trace("peopleResult, {}", response.getBody());
            log.debug("getPeopleByPage completed");

            return response.getBody();


        });
    }

}
