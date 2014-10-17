package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 17.10.14.
 */
public class AuthorizationException extends Exception {
    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
