package com.nathanielbennett.tweeter.server.exceptions;

public class UserAlreadyFollowedException extends RuntimeException {
    public UserAlreadyFollowedException(String reason) {
        super("[BadRequestError] " + reason);
    }
}
