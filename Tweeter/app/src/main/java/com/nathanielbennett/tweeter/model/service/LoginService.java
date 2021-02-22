package com.nathanielbennett.tweeter.model.service;

import java.io.IOException;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.util.ByteArrayUtils;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginService {

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

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
