package ru.gavrilov.aop.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public final class Ioc {
    private Ioc() {
    }

    public static <T> T createObject(Class<? extends T> type) {
        T t = create(type);

        return replaceWithProxy(t, type);
    }

    private static <T> T replaceWithProxy(T t, Class<? extends T> implClass) {
        //todo use cg-lib when class has not interfaces or interfaces without method
        InvocationHandler handler = new LoggingHandler(t, implClass);

        return (T) Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), handler);
    }

    private static <T> T create(Class<? extends T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("*** SneakyThrows ***");
            e.printStackTrace();
        }
        return null;
    }
}
