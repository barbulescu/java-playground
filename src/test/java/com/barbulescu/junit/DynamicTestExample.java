package com.barbulescu.junit;

import org.junit.jupiter.api.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class DynamicTestExample {

    private TestReporter testReporter;

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @BeforeEach
    void beforeEach(TestReporter testReporter) {
        this.testReporter = testReporter;
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll");
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromIntStream() {
        // Generates tests for the first 10 even integers.
        return IntStream.iterate(0, n -> n + 2)
                .limit(10)
                .mapToObj(this::prepareDynamicTest);
    }

    private DynamicTest prepareDynamicTest(int n) {
        String name = String.format("test -> %02d", n);
        return dynamicTest(name, () -> executeDynamicTest(name, n));
    }

    private void executeDynamicTest(String name, int n) {
        System.out.println("executing " + name);
        testReporter.publishEntry("custom_key", name);
        assertEquals(n % 2, 0);
    }
}
