package ru.gavrilov.reflection;

/**
 * Created by tully.
 */
public class FinalField {
    private final int value;

    public FinalField(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
