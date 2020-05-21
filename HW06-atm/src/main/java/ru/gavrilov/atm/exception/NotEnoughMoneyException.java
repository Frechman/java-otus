package ru.gavrilov.atm.exception;

/**
 * @author gavrilov-sv
 * created on 20.05.2020
 */
public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException() {
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}

