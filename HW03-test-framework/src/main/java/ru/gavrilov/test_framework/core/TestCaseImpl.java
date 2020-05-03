package ru.gavrilov.test_framework.core;

import ru.gavrilov.test_framework.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestCaseImpl implements TestCase {

    private final Object instanceClass;

    private final Method testMethod;

    private final List<Method> beforeMethods;

    private final List<Method> afterMethods;

    private List<Throwable> errors = new ArrayList<>();

    public TestCaseImpl(Class<?> clazz, Method method, List<Method> beforeMethods, List<Method> afterMethods) {
        this.instanceClass = ReflectionHelper.newInstance(clazz);
        this.testMethod = method;
        this.beforeMethods = beforeMethods;
        this.afterMethods = afterMethods;
    }

    @Override
    public void run() {
        try {
            setUp();

            ReflectionHelper.callMethod(instanceClass, testMethod);

        } catch (Throwable t) {//todo упростил. Разные списки для AssertionError и Throwable
            errors.add(t);
        } finally {
            try {
                tearDown();
            } catch (Throwable t) {
                errors.add(t);
            }
        }
    }

    private void setUp() {
        for (Method method : beforeMethods) {
            ReflectionHelper.callMethod(instanceClass, method);
        }
    }

    private void tearDown() {
        for (Method method : afterMethods) {
            ReflectionHelper.callMethod(instanceClass, method);
        }
    }

    public List<Throwable> getErrors() {
        return errors;
    }
}
