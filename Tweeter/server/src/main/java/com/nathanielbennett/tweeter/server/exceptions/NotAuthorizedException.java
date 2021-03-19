package com.nathanielbennett.tweeter.server.exceptions;

public class NotAuthorizedException extends Exception {
    public NotAuthorizedException(String reason) {
        super(reason);
    }
}
