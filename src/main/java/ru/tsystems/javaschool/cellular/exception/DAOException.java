package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 09.10.14.
 */
public class DAOException extends Exception {

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
