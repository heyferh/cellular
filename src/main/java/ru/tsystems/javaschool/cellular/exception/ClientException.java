package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 16.10.14.
 */
public class ClientException extends Exception {
    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
