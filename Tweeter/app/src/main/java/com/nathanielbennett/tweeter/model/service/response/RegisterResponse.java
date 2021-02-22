package com.nathanielbennett.tweeter.model.service.response;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;

public class RegisterResponse extends Response {

    private User user;
    private AuthToken authToken;

    /**
     * Creates a response indicating that the attempted registration was unsuccessful.
     *
     * @param message an error message describing why the registration was unsuccessful.
     */
    public RegisterResponse(String message) {
        super(message);
    }

    /**
     * Creates a response indicating that the user was successfully registered.
     *
     * @param user the now registered and logged in user.
     * @param authToken the auth token representing this user's session with the server.
     */
    public RegisterResponse(User user, AuthToken authToken) {
        super();
        this.user = user;
        this.authToken = authToken;
    }


    /**
     * Returns the logged in user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the auth token.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

}
