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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@RestController
public class PeopleResourceController {

    private static final Logger log = LoggerFactory.getLogger(PeopleResourceController.class);

    @Autowired
    private PeopleService peopleService;

    /**
     * Restful class controller for people endpoint
     *
     * @author javigs82
     */


    @RequestMapping(value = "/people", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<People>> getAllPeopleSorted(@RequestParam(name = "sort") String sort) throws ExecutionException, InterruptedException {
        log.info("getting all people sorted by {}", sort);
        //map sort.
        SortValues sortValue = SortValues.get(sort);

        if (sortValue == null) {
            log.warn("invalid sort value {}", sort);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        switch (sortValue) {
            case NAME_ASC:
                return new ResponseEntity<>(peopleService.getPeopleSortedByNameASC().orElse(Collections.EMPTY_LIST), HttpStatus.OK);
            case NAME_DESC:
                return new ResponseEntity<>(peopleService.getPeopleSortedByNameDESC().orElse(Collections.EMPTY_LIST), HttpStatus.OK);
            case CREATED_DATE_ASC:
                return new ResponseEntity<>(peopleService.getPeopleSortedByCreatedASC().orElse(Collections.EMPTY_LIST), HttpStatus.OK);
            case CREATED_DATE_DESC:
                return new ResponseEntity<>(peopleService.getPeopleSortedByCreatedDESC().orElse(Collections.EMPTY_LIST), HttpStatus.OK);
            default: //default name asc
                return new ResponseEntity<>(peopleService.getPeopleSortedByNameDESC().orElse(Collections.EMPTY_LIST), HttpStatus.OK);
        }

    }

    /**
     * Private enum to validate people sort value. Allowed values are: +name, -name, +
     */
    enum SortValues {
        NAME_ASC("+name"), NAME_DESC("-name"), CREATED_DATE_ASC("+created"), CREATED_DATE_DESC("-created");
        static final Map<String, SortValues> ENUM_MAP;

        // Build an immutable map of String name to enum pairs.
        // Any Map impl can be used.
        static {
            Map<String, SortValues> map = new ConcurrentHashMap<>();
            for (SortValues instance : SortValues.values()) {
                map.put(instance.getRequestSortValue(), instance);
            }
            ENUM_MAP = Collections.unmodifiableMap(map);
        }

        String requestSortValue;

        SortValues(String requestSortValue) {
            this.requestSortValue = requestSortValue;
        }

        public static SortValues get(String requestSortValue) {
            return ENUM_MAP.get(requestSortValue);
        }

        String getRequestSortValue() {
            return this.requestSortValue;
        }
    }


}
