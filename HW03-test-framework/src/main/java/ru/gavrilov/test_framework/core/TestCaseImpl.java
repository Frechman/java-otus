package ru.gavrilov.test_framework.core;

import ru.gavrilov.test_framework.annotation.After;
import ru.gavrilov.test_framework.annotation.Before;
import ru.gavrilov.test_framework.reflection.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.List;

public class TestCaseImpl implements TestCase {

    private final Object instanceClass;

    private final String testMethodName;

    private final List<Method> beforeMethods;

    private final List<Method> afterMethods;

    public TestCaseImpl(Class<?> clazz, String testMethodName) {
        this.instanceClass = ReflectionHelper.newInstance(clazz);
        this.testMethodName = testMethodName;
        this.beforeMethods = ReflectionHelper.getMethodsWithAnnotation(Before.class, clazz.getDeclaredMethods());
        this.afterMethods = ReflectionHelper.getMethodsWithAnnotation(After.class, clazz.getDeclaredMethods());
    }

    @Override
    public void run() throws RuntimeException {
        for (Method method : beforeMethods) {
            ReflectionHelper.callMethod(instanceClass, method.getName());
        }

        ReflectionHelper.callMethod(instanceClass, testMethodName);

        for (Method method : afterMethods) {
            ReflectionHelper.callMethod(instanceClass, method.getName());
        }
    }
}
