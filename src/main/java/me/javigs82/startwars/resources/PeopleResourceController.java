package me.javigs82.startwars.resources;

import me.javigs82.startwars.domain.People;
import me.javigs82.startwars.domain.PeopleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class PeopleResourceController {

    private static Logger log = LoggerFactory.getLogger(PeopleResourceController.class);

    @Autowired
    private PeopleService peopleService;
    /**
     * Restful class controller for people endpoint
     *
     * @author javigs82
     */

    @RequestMapping(value = "/people", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<People>> getAllPeople(@RequestParam(name = "limit") int limit) throws ExecutionException, InterruptedException {
        log.info("getting all people with limit {}", limit);
        List<People> people = peopleService.getPeopleSortedBy();
        log.info("people got with size {}", people.size());
        return new ResponseEntity<>(people, HttpStatus.OK);
    }
}
