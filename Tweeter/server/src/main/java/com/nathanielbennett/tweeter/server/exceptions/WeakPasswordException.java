package com.nathanielbennett.tweeter.server.exceptions;

public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException(String reason) {
        super("[BadRequestError] " + reason);
    }

}
