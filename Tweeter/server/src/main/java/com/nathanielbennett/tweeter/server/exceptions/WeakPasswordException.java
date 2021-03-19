package com.nathanielbennett.tweeter.server.exceptions;

public class WeakPasswordException extends Exception {
    public WeakPasswordException(String reason) {
        super(reason);
    }

}
