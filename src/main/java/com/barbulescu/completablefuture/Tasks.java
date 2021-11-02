package com.barbulescu.completablefuture;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.TimeUnit.*;

public class Tasks {
    public static void main(String[] args) throws Exception {
        try (TaskExecutor wrapper = new TaskExecutor()) {
//        case1(wrapper);
//            thenAcceptIsNotExecutedOnError(wrapper);
//            allOfWithError(wrapper);
            withTimeout(wrapper);
        }
    }

    private static void withTimeout(TaskExecutor wrapper) throws ExecutionException, InterruptedException, TimeoutException {
        wrapper.runAsync(new Task1())
                .orTimeout(2, SECONDS)
                .get(30, SECONDS);
    }

    private static void allOfWithError(TaskExecutor wrapper) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture.allOf(
                wrapper.runAsync(new FailingTask()),
                wrapper.runAsync(new Task3("second"))
        ).get(10, SECONDS);
    }


    private static void thenAcceptIsNotExecutedOnError(TaskExecutor wrapper) throws ExecutionException, InterruptedException, TimeoutException {
        wrapper.runAsync(new FailingTask())
                .thenAccept(previousResult -> log("second task"))
                .get(10, SECONDS);
    }

    private static void case1(TaskExecutor wrapper) throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<Void> firstSet = allOf(
                wrapper.runAsync(new Task1()),
                wrapper.runAsync(new Task1())
        );

        CompletableFuture<Void> secondSet = wrapper
                .supplyAsync(new Task2())
                .thenComposeAsync(previousValue -> allOf(
                        wrapper.runAsync(new Task3(previousValue)),
                        wrapper.runAsync(new Task3(previousValue)),
                        wrapper.runAsync(new Task3(previousValue)),
                        wrapper.runAsync(new Task3(previousValue))
                ));

        CompletableFuture<Void> global = firstSet.thenCompose(unused -> secondSet);

        global.get(5010, SECONDS);
        log("Done.");


    }

    static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.interrupted();
            e.printStackTrace();
        }
    }

    static void log(String str) {
        System.out.println("[" + LocalTime.now().toString() + " " + Thread.currentThread().getName() + "] - " + str);
    }

}


