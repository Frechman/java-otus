package ru.gavrilov.aop.dynamic_proxy;

public interface Computable {

    void compute(int param);

    void compute(int param1, int param2);

    void compute(String param);

    void something(int param);
}
