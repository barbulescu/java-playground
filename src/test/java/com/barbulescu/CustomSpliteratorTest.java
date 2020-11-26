package com.barbulescu;

import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

import static java.util.Spliterators.*;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Streams.*;

class CustomSpliteratorTest {

    @Test
    void basicTest() {
        List<Pair> result = execute("a", "3", "b", "2", "c", "6");

        assertThat(result).containsExactly(
                new Pair("a", 3),
                new Pair("b", 2),
                new Pair("c", 6)
        );
    }

    @Test
    void partial() {
        List<Pair> result = execute("a", "3", "b");
        assertThat(result).containsExactly(
                new Pair("a", 3)
        );
    }

    @Test
    void empty() {
        List<Pair> result = execute();
        assertThat(result).isEmpty();
    }

    private List<Pair> execute(String...values) {
        Spliterator<String> delegate = List.of(values).spliterator();
        CustomSpliterator customSpliterator = new CustomSpliterator(delegate);
        return stream(iterator(customSpliterator))
                .collect(toUnmodifiableList());
    }

}