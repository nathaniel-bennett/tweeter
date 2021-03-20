package com.nathanielbennett.tweeter.server.exceptions;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String reason) {
        super("[BadRequestError] " + reason);
    }
}
