package me.javigs82.startwars.domain;

import me.javigs82.startwars.domain.model.ModelResult;
import me.javigs82.startwars.domain.model.People;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface PeoplePort {

    CompletableFuture<ModelResult<People>> getPeopleByPage(int page);

}
