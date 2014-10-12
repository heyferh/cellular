package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 12.10.14.
 */
public class TariffException extends Exception {
    String msg;

    public TariffException(String message) {
        super(message);
        this.msg = message;
    }
}
