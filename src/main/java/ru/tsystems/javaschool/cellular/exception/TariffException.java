package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 17.10.14.
 */
public class TariffException extends Exception {
    public TariffException() {
    }

    public TariffException(String message) {
        super(message);
    }

    public TariffException(String message, Throwable cause) {
        super(message, cause);
    }
}
