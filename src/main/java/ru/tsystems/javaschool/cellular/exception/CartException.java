package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 26.11.14.
 */
public class CartException extends Exception {

    public CartException() {
    }

    public CartException(String message) {
        super(message);
    }

    public CartException(String message, Throwable cause) {
        super(message, cause);
    }
}
