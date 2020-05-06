package ru.gavrilov.test_framework.service;

import java.io.PrintStream;

public class ConsoleOutputService implements OutputService {

    private final PrintStream out = System.out;

    @Override
    public void out(String message) {
        out.println(message);
    }
}
