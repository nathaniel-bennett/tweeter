package com.nathanielbennett.tweeter.server.exceptions;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String reason) {
        super("[InternalServerError] " + reason);
    }
}
