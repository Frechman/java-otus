package ru.gavrilov.reflection.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoking method:" + method); //added other logic - logging
        long start = System.nanoTime();

        Object invoke = method.invoke(new MyInterface() {}, args);

        long end = System.nanoTime();

        System.out.println("elapsed time:" + (end - start)); //added other logic - profiling
        return invoke;
    }
}