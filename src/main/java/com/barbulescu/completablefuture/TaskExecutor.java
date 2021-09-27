package com.barbulescu.completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.barbulescu.completablefuture.Tasks.log;

class TaskExecutor implements AutoCloseable {
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final List<CompletableFuture<?>> futures = new ArrayList<>();

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, executorService);
        futures.add(future);
        return future;
    }

    @Override
    public void close() {
        log("shutdown done");
        executorService.shutdown();
    }

    public <T> CompletableFuture<T> supplyAsync(Supplier<T> task) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(task, executorService);
        futures.add(future);
        return future;
    }

    public void cancel() {
        for (CompletableFuture<?> future : futures) {
            future.cancel(true);
        }
        futures.clear();
    }
}
