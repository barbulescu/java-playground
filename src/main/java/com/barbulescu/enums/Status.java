package com.barbulescu.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

@RequiredArgsConstructor
public enum Status {
    ACTIVE("active"), DISABLED("disabled");
    private final String esValue;

    public String esValue() {
        return esValue;
    }

    private static final Map<String, Status> values = Arrays.stream(values())
            .collect(toUnmodifiableMap(Status::esValue, Function.identity()));

    public static Status of(String esValue) {
        return values.get(esValue);
    }
}
