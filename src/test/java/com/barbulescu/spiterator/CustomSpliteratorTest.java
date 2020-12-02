package com.barbulescu.spiterator;

import com.barbulescu.spliterator.CustomSpliterator;
import com.barbulescu.spliterator.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Spliterator;

import static java.util.Spliterators.*;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Streams.*;

class CustomSpliteratorTest {

    @Test
    void basicTest() {
        List<Pair> result = execute("a", "3", "b", "2", "c", "6");

        assertThat(result).containsExactly(
                Pair.builder().name("a").position("3").build(),
                Pair.builder().name("b").position("2").build(),
                Pair.builder().name("c").position("6").build()
        );
    }

    @Test
    void partial() {
        List<Pair> result = execute("a", "3", "b");
        assertThat(result).containsExactly(
                Pair.builder().name("a").position("3").build()
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