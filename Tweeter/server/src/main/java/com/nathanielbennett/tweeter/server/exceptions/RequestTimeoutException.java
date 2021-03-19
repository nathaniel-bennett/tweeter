package com.nathanielbennett.tweeter.server.exceptions;

public class RequestTimeoutException extends RuntimeException {
    public RequestTimeoutException(String reason) {
        super(reason);
    }
}
