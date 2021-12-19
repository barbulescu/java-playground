package com.barbulescu.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    @Test
    void ofMethod() {
        assertThat(Status.of("active")).isEqualTo(Status.ACTIVE);
    }
}