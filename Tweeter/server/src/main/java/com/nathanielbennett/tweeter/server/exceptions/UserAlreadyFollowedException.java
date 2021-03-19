package com.nathanielbennett.tweeter.server.exceptions;

public class UserAlreadyFollowedException extends Exception {
    public UserAlreadyFollowedException(String reason) {
        super(reason);
    }
}
