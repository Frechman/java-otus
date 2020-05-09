package ru.gavrilov.reflection.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author gavrilov-sv
 * created on 04.05.2020
 */
public class DynamicProxy {
    public static void main(String[] args) {
        InvocationHandler handler = new MyInvocationHandler();
        MyInterface proxy = (MyInterface) Proxy.newProxyInstance(
                MyInterface.class.getClassLoader(),
                new Class[] { MyInterface.class },
                handler);
        System.out.println(proxy.getClass());
        System.out.println(proxy.getStr());
    }
}
