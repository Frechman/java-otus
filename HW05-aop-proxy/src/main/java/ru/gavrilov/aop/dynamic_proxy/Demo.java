package ru.gavrilov.aop.dynamic_proxy;

public class Demo {
    public static void main(String[] args) {
        new Demo().action();
    }

    public void action() {
        Computable calculator = Ioc.createObject(Calculator.class);
        calculator.compute(Integer.MAX_VALUE);
        calculator.compute(6);
        calculator.compute(1);

        Ioc.<Computable>createObject(Calculator.class).something(0); //no logging

        Ioc.<Computable>createObject(Calculator.class).compute(Integer.MIN_VALUE);
    }
}
