package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 09.10.14.
 */
public class DAOException extends RuntimeException {
    String msg;

    public DAOException(String message) {
        super(message);
        this.msg = message;
    }
}
