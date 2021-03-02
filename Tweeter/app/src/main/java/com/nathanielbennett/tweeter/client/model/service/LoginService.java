package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;

import java.io.IOException;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginService extends Service {

    /**
     *
     * @param request The username and password of the user that intends to be logged in.
     * @return Authorization and user information if the login succeeded, or an error message
     * otherwise.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public LoginResponse login(LoginRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null login request passed into LoginService");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return new LoginResponse("A Username is required to sign in (please enter)");
        } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return new LoginResponse("A password is required to sign in (please enter)");
        }

        return serverFacade.login(request);
    }
}
