package com.barbulescu.completablefuture;

import java.util.function.Supplier;

import static com.barbulescu.completablefuture.Tasks.log;
import static com.barbulescu.completablefuture.Tasks.delay;

class Task2 implements Supplier<String> {

    @Override
    public String get() {
        log("task2 >>>");
        delay(5_000);
        log("task2 <<<");
        return "task3";
    }
}
