package com.barbulescu.completablefuture;

import static com.barbulescu.completablefuture.Tasks.log;

class FailingTask implements Runnable {
    @Override
    public void run() {
        log("failing task >>>");
        Tasks.delay(1_000);
        throw new RuntimeException("from failing task");
    }
}
