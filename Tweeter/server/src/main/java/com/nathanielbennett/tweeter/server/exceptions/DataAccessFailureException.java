package com.nathanielbennett.tweeter.server.exceptions;

public class DataAccessFailureException extends Exception {
    public DataAccessFailureException(String reason) {
        super(reason);
    }
}
