package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.server.DataCache;
import com.nathanielbennett.tweeter.server.exceptions.InvalidPasswordException;

public class LoginDAO {
    private static final AuthToken authToken1 = new AuthToken();
    private static final DataCache dc = DataCache.getInstance();

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        if (request.getPassword().equals("dummyUser") && request.getUsername().equals("dummyUser")) {
            User user = dc.getUser("dummyUser");
            dc.setAuthToken(authToken1);
            return new LoginResponse(user, authToken1);

        } else {
            throw new InvalidPasswordException("Failed to authenticate user on login");
        }
    }
}
