package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 12.10.14.
 */
public class ContractException extends Exception {
    String msg;

    public ContractException(String message) {
        super(message);
        this.msg = message;
    }
}
