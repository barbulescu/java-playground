package com.barbulescu.completablefuture;

import static com.barbulescu.completablefuture.Tasks.log;

class Task1 implements Runnable {
    @Override
    public void run() {
        log("task1 >>>");
        Tasks.delay(14_000);
        log("task1 <<<");
    }
}
