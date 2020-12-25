package com.barbulescu.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
class DisplayNameGeneratorDemo {

    @Test
    @DisplayName("basic junit test")
    void basic() {

    }

    @Test
    void basic_test_with_underscore() {

    }

    @Nested
    class SimpleNested {
        @Test
        void generator_is_inherited_in_nested() {

        }
    }

    @Nested
    @IndicativeSentencesGeneration(separator = " -> ", generator = ReplaceUnderscores.class)
    @DisplayName("custom")
    class IndicativeSentence {
        @Test
        void with_indicative_sentence() {

        }
    }
}
