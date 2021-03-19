package com.nathanielbennett.tweeter.server.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String reason) {
        super(reason);
    }
}
