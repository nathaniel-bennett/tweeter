package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginService extends Service {

    public LoginResponse login(LoginRequest request) {
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
