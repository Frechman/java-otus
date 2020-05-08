package ru.gavrilov.aop.dynamic_proxy;

public class Demo {
    public static void main(String[] args) {
        new Demo().action();
    }

    public void action() {
        Computable calculator = Ioc.createCalculator();
        calculator.compute(Integer.MAX_VALUE);
        calculator.compute(6);
        calculator.compute(1);

        Ioc.createCalculator().something(0); //no logging

        Ioc.createCalculator().compute(Integer.MIN_VALUE);
    }
}
