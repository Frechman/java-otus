package ru.gavrilov.test_framework;

import ru.gavrilov.test_framework.core.TestEngine;
import ru.gavrilov.test_framework.service.ConsoleOutputService;

public final class TestRunner {

    private TestRunner() {
    }

    public static void runTests(Class<?> clazz) {
        new TestEngine(clazz, new ConsoleOutputService()).runTests();
    }

    public static void runTests(String className) {
        try {
            Class<?> aClass = Class.forName(className);
            runTests(aClass);
        } catch (ClassNotFoundException e) {
            System.out.println("class \"" + className + "\" not found");
        }
    }
}
