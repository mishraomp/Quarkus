package com.om.example;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class EmployeeService {
    public CompletionStage<String> greeting(final String name) {
        return CompletableFuture.supplyAsync(() -> "hello " + name);
    }
}
