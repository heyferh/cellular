package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 09.10.14.
 */
public class DAOException extends RuntimeException {

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
