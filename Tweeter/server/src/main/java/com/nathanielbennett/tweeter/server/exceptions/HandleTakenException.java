package com.nathanielbennett.tweeter.server.exceptions;

public class HandleTakenException extends RuntimeException {
    public HandleTakenException(String reason) {
        super(reason);
    }
}
