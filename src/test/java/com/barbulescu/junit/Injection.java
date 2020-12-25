package com.barbulescu.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

class Injection {

    private final TestInfo testInfo;

    Injection(TestInfo testInfo) {
        this.testInfo = testInfo;
    }

    @CustomTest
    void with_test_info() {
        System.out.println(testInfo.getDisplayName());
    }

    @Test
    void reportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("a status message");
    }

}
