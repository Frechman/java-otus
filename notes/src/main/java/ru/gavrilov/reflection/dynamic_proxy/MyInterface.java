package ru.gavrilov.reflection.dynamic_proxy;

/**
 * @author gavrilov-sv
 * created on 04.05.2020
 */
public interface MyInterface {

    default String getStr(){
        return "string";
    }
}
