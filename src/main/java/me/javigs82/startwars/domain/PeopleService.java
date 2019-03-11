package me.javigs82.startwars.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
public class PeopleService {

    private static Logger log = LoggerFactory.getLogger(PeopleService.class);

    @Autowired
    @Qualifier ("peopleAdapter")
    private PeoplePort peoplePort;


    //TODO: implement sorting here
    //In a wel done this method should
    //catch exception and thrown the proper one
    public List<People> getPeopleSortedBy () throws InterruptedException, ExecutionException {
        return peoplePort.getAll();
    }



}
