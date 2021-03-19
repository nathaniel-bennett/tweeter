package com.nathanielbennett.tweeter.server.exceptions;

public class DataAccessFailureException extends RuntimeException {
    public DataAccessFailureException(String reason) {
        super(reason);
    }
}
