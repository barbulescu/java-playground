package com.barbulescu.batch;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.StreamSupport.stream;

public class Main {
    private static final ExecutorService executor = newFixedThreadPool(4);

    public static void main(String[] args) throws InterruptedException {
        IntStream stream = IntStream.range(0, 100);
        BatchSpliterator<Integer> spliterator = new BatchSpliterator<>(stream.spliterator(), 7);
        stream(spliterator, false)
                .map(Main::toCF)
                .forEach(x -> {
                    try {
                        Integer a = x.get();
                        System.out.println(a);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });

        Thread.sleep(5000);
        executor.shutdown();
    }

    private static CompletableFuture<Integer> toCF(List<Integer> batch) {
        return supplyAsync(() -> processBatch(batch), executor);
    }

    private static int processBatch(List<Integer> batch) {
        System.out.println(Thread.currentThread().getName());
        return batch.stream().mapToInt(value -> value).sum();
    }

}
