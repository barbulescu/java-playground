package com.barbulescu.junit.extension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
public @interface Timeout {
    long value() default 10;

    TimeUnit unit() default TimeUnit.SECONDS;
}
