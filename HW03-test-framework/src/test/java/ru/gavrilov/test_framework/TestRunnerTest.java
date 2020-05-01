package ru.gavrilov.test_framework;

import org.junit.jupiter.api.Test;

public class TestRunnerTest {

    @Test
    void shouldTrue() {
        TestRunner.runTests("ru.gavrilov.test_framework.ExampleClassForTesting");
    }
}