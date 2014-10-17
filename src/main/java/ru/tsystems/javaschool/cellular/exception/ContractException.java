package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 17.10.14.
 */
public class ContractException extends Exception {
    public ContractException() {
    }

    public ContractException(String message) {
        super(message);
    }

    public ContractException(String message, Throwable cause) {
        super(message, cause);
    }
}
