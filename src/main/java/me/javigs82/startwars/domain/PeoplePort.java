package me.javigs82.startwars.domain;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PeoplePort {


	List<People> getAll() throws InterruptedException, ExecutionException;

 
}
