package com.nathanielbennett.tweeter.server.exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String reason) {
        super(reason);
    }
}
