package ru.gavrilov;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class HelloOtus {

    public static void main(String[] args) {
        System.out.println("Hello, Otus!");
        ArrayList<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        System.out.println(Lists.reverse(list));
    }
}
