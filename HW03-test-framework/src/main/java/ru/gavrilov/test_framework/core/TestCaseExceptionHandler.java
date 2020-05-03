package ru.gavrilov.test_framework.core;

import java.util.ArrayList;
import java.util.List;

public class TestCaseExceptionHandler implements TestCase {

    private TestCase testCase;

    private List<Throwable> errors = new ArrayList<>();

    public TestCaseExceptionHandler(TestCase testCase) {
        this.testCase = testCase;
    }

    public void run() {
        try {
            testCase.run();
        } catch (AssertionError | Exception e) {
            errors.add(e);
        }
    }

    public List<Throwable> getErrors() {
        return errors;
    }
}
