package com.barbulescu.completablefuture;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.CompletableFuture.allOf;

public class Tasks {
    public static void main(String[] args) throws Exception {
        try (TaskExecutor wrapper = new TaskExecutor()) {
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

            global.get(5010, TimeUnit.SECONDS);
            log("Done.");

        }
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


