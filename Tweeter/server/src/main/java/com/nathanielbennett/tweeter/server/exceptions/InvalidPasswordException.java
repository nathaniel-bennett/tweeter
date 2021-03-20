package com.nathanielbennett.tweeter.server.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String reason) {
        super("[BadRequestError] " + reason);
    }
}
