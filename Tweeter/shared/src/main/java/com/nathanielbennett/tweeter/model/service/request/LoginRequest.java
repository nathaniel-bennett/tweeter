package com.nathanielbennett.tweeter.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class LoginRequest extends AdmissionRequest {

    public LoginRequest() {
    }

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public LoginRequest(String username, String password) {
        super(username, password);
    }
}
