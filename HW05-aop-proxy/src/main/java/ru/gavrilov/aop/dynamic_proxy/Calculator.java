package ru.gavrilov.aop.dynamic_proxy;

public class Calculator implements Computable {

    @Log
    @Override
    public void compute(int param) { }

    @Log
    @Override
    public void compute(int param1, int param2) { }

    @Log
    @Override
    public void compute(String param) { }

    @Override
    public void something(int param) { }
}
