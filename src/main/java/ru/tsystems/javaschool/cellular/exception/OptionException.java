package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 17.10.14.
 */
public class OptionException extends Exception {
    public OptionException() {
    }

    public OptionException(String message) {
        super(message);
    }

    public OptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
