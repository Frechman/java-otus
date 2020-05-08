package ru.gavrilov.aop.dynamic_proxy;

public class Calculator implements Computable {

    @Log
    @Override
    public void compute(int param) { }

    @Override
    public void something(int param) { }
}
