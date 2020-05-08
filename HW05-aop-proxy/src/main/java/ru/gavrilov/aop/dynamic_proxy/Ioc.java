package ru.gavrilov.aop.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public final class Ioc {
    private Ioc() {
    }

    public static Computable createCalculator() {
        InvocationHandler handler = new ComputableHandler(new Calculator());
        return (Computable) Proxy.newProxyInstance(
                Computable.class.getClassLoader(),
                new Class<?>[]{Computable.class},
                handler);
    }
}
