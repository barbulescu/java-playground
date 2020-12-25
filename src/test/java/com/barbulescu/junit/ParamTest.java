package com.barbulescu.junit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ParamTest {

    static Stream<String> with_default_local_method_source() {
        return Stream.of("a", "b");
    }

    @ParameterizedTest()
    @MethodSource
    void with_default_local_method_source(String value) {

    }

    static Stream<String> m1() {
        return Stream.of("a", "b");
    }

    static Stream<String> m2() {
        return Stream.of("c", "d");
    }


    @ParameterizedTest
    @MethodSource({"m1", "m2"})
    void multiple_factories(String value) {

    }
}
