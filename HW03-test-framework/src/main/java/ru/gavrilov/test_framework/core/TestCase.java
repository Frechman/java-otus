package ru.gavrilov.test_framework.core;

import ru.gavrilov.test_framework.annotation.After;
import ru.gavrilov.test_framework.annotation.Before;
import ru.gavrilov.test_framework.reflection.Helper;

import java.lang.reflect.Method;
import java.util.List;

public class TestCase implements Test {

    private final Object instanceClass;

    private final String testMethodName;

    private final List<Method> beforeMethods;

    private final List<Method> afterMethods;

    public TestCase(Class<?> clazz, String testMethodName) {
        this.instanceClass = Helper.newInstance(clazz);
        this.testMethodName = testMethodName;
        this.beforeMethods = Helper.getMethodsWithAnnotation(Before.class, clazz.getDeclaredMethods());
        this.afterMethods = Helper.getMethodsWithAnnotation(After.class, clazz.getDeclaredMethods());
    }

    @Override
    public void run() throws RuntimeException {
        for (Method method : beforeMethods) {
            Helper.callMethod(instanceClass, method.getName());
        }

        Helper.callMethod(instanceClass, testMethodName);

        for (Method method : afterMethods) {
            Helper.callMethod(instanceClass, method.getName());
        }
    }
}
