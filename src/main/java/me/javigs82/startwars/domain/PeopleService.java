package me.javigs82.startwars.domain;

import me.javigs82.startwars.domain.model.People;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface PeopleService {
    Optional<List<People>> getPeopleSortedByNameASC() throws ExecutionException, InterruptedException;

    Optional<List<People>> getPeopleSortedByNameDESC() throws ExecutionException, InterruptedException;

    Optional<List<People>> getPeopleSortedByCreatedASC() throws ExecutionException, InterruptedException;

    Optional<List<People>> getPeopleSortedByCreatedDESC() throws ExecutionException, InterruptedException;

    enum SortDirection {
        ASC, DESC
    }
}
