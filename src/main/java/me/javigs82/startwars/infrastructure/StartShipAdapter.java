package me.javigs82.startwars.infrastructure;

import me.javigs82.startwars.domain.StartShipPort;
import me.javigs82.startwars.domain.model.StartShip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component("startShipAdapter")
public class StartShipAdapter implements StartShipPort {

    private static final Logger log = LoggerFactory.getLogger(StartShipAdapter.class);

    //tmp map to avoid request the same startship multiples times
    private static Map<String, StartShip> cacheMap = new ConcurrentHashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<StartShip> getStartShipByUrl(String url) {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("getStartShipByUrl {}", url);

            //check in cache to avoid request
            if (cacheMap.containsKey(url)) {
                log.debug("getStartShipByUrl cached");
                return cacheMap.get(url);
            }

            //Set the headers you need send, like User-Agent to avoid 403
            final HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/72.0.3626.119 Safari/537.36");
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            final HttpEntity<StartShip> httpEntity = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("format", "json");

            final ResponseEntity<StartShip> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    httpEntity, new ParameterizedTypeReference<StartShip>() {
                    });
            log.trace("StartShip, {}", response.getBody());
            log.debug("getStartShipByUrl completed");

            StartShip result = response.getBody();
            //add to cache
            cacheMap.put(url, result);

            return result;
        });
    }
}
