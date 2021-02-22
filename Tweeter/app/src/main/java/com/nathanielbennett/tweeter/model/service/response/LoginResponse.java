package com.nathanielbennett.tweeter.model.service.response;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;

/**
 * A response for a {@link com.nathanielbennett.tweeter.model.service.request.LoginRequest}.
 */
public class LoginResponse extends Response {

    private User user;
    private AuthToken authToken;

    /**
     * Creates a response indicating that the login attempt was unsuccessful.
     *
     * @param message an error message describing why the login was unsuccessful.
     */
    public LoginResponse(String message) {
        super(message);
    }

    /**
     * Creates a response indicating that the login attempt was successful.
     *
     * @param user the now logged in user.
     * @param authToken the auth token representing this user's session with the server.
     */
    public LoginResponse(User user, AuthToken authToken) {
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
