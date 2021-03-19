package com.nathanielbennett.tweeter.server.exceptions;

public class RequestTimeoutException extends Exception {
    public RequestTimeoutException(String reason) {
        super(reason);
    }
}
