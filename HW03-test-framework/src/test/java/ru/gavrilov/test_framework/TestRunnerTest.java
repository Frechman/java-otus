package ru.gavrilov.test_framework;

import org.junit.jupiter.api.Test;

public class TestRunnerTest {

    @Test
    void shouldTrueAndShowStatisticsOnConsole() {
        TestRunner.runTests("ru.gavrilov.test_framework.ExampleClassForTesting");
    }
}