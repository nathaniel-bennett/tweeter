package com.nathanielbennett.tweeter.server.exceptions;

public class HandleTakenException extends Exception {
    public HandleTakenException(String reason) {
        super(reason);
    }
}
