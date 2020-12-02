package com.barbulescu.spliterator;

import lombok.Builder;
import lombok.Value;

@Builder
@Value(staticConstructor = "of")
public class Pair {
    String name;
    int position;

    public static class PairBuilder {
        public PairBuilder position(String value) {
            position = Integer.parseInt(value);
            return this;
        }
    }
}

