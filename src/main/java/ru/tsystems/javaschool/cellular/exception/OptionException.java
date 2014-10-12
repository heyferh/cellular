package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 12.10.14.
 */
public class OptionException extends Exception {
    String msg;

    public OptionException(String message) {
        super(message);
        this.msg = message;
    }
}
