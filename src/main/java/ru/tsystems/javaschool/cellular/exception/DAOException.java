package ru.tsystems.javaschool.cellular.exception;

/**
 * Created by ferh on 09.10.14.
 */
public class DAOException extends Exception {

    public enum ERROR_CODE {
        OBJECT_NOT_FOUND,
        OBJECT_ALREADY_EXISTS,
        PERSISTENCE_EXCEPTION,
    }

    public ERROR_CODE getErrorCode() {
        return errorCode;
    }

    private ERROR_CODE errorCode;

    public DAOException(ERROR_CODE errorCode) {
        this.errorCode = errorCode;
    }

    public DAOException(ERROR_CODE errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException() {
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
