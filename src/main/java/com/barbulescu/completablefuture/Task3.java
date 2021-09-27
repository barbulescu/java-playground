package com.barbulescu.completablefuture;

import java.util.concurrent.atomic.AtomicInteger;

import static com.barbulescu.completablefuture.Tasks.log;

class Task3 implements Runnable {
    private static final AtomicInteger index = new AtomicInteger(1);
    private final String name;

    Task3(String name) {
        this.name = name + "_" + index.getAndIncrement();
    }

    @Override
    public void run() {
        log(name + " >>>");
        Tasks.delay(4_000);
        log(name + " <<<");
    }
}
