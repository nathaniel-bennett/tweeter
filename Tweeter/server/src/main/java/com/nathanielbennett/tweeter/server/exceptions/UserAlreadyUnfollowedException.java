package com.nathanielbennett.tweeter.server.exceptions;

public class UserAlreadyUnfollowedException extends Exception {
    public UserAlreadyUnfollowedException(String reason) {
        super(reason);
    }
}
