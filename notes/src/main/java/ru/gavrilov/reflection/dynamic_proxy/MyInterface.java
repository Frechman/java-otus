package ru.gavrilov.reflection.dynamic_proxy;

public interface MyInterface {

    default String getStr(){
        return "string";
    }
}
