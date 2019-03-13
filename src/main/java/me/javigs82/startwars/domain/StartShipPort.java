package me.javigs82.startwars.domain;

import me.javigs82.startwars.domain.model.StartShip;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface StartShipPort {

    CompletableFuture<StartShip> getStartShipByUrl(String url);


}
