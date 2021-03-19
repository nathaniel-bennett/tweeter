package com.nathanielbennett.tweeter.server.exceptions;

public class UserAlreadyUnfollowedException extends RuntimeException {
    public UserAlreadyUnfollowedException(String reason) {
        super(reason);
    }
}
