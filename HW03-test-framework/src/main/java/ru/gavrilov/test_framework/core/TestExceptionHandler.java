package ru.gavrilov.test_framework.core;

import java.util.ArrayList;
import java.util.List;

public class TestExceptionHandler implements Test {

    private Test test;

    private List<Throwable> errors = new ArrayList<>();

    public TestExceptionHandler(Test test) {
        this.test = test;
    }

    public void run() {
        try {
            test.run();
        } catch (AssertionError | Exception e) {
            errors.add(e);
        }
    }

    public List<Throwable> getErrors() {
        return errors;
    }
}
