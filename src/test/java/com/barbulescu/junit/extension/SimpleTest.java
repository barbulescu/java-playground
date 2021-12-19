package com.barbulescu.junit.extension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExtendWith(ExecutorServiceExtension.class)
public class SimpleTest {

    @Timeout(1)
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Test
    void timeout() {
        executorService.submit(() -> {
            try {
                System.out.println("1");
                Thread.sleep(1_000);
                System.out.println("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
