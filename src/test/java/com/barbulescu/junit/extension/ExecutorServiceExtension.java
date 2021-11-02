package com.barbulescu.junit.extension;

import lombok.Value;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

public class ExecutorServiceExtension implements BeforeEachCallback, AfterEachCallback {
    private static final Namespace NAME = Namespace.create("EXECUTOR_SERVICE");

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.out.println("before");
    }

    private void stop(ExecutorService it, Timeout timeout) {
        it.shutdown();
        try {
            it.awaitTermination(timeout.value(), timeout.unit());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        System.out.println("after");
        extensionContext.getTestInstances()
                .stream()
                .flatMap(it -> it.getAllInstances().stream())
                .flatMap(it -> find(it))
                .forEach(pair -> {
                    ExecutorService executorService = pair.executorService();
                    Timeout timeout = pair.timeout();
                    stop(executorService, timeout);
                });
    }

    private static Stream<Pair> find(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(it -> it.getType().equals(ExecutorService.class))
                .peek(f -> f.setAccessible(true))
                .map(f -> new Pair(object, f))
;
    }

    @Value
    private static class Pair {
        Object object;
        Field field;

        public ExecutorService executorService() {
            try {
                return (ExecutorService) field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Timeout timeout() {
            return field.getAnnotation(Timeout.class);
        }
    }
}
