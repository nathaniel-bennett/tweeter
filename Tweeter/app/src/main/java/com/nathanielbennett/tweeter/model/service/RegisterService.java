package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;

public class RegisterService extends Service {

    /**
     * Attempts to register a new user with the given {@link RegisterRequest} information. Returns a
     * response containing the new user and its associated Auth Token on success, or an error
     * message on failure
     * @param request The user information used to register a new User.
     * @return A response indicating either success or failure.
     */
    public RegisterResponse register(RegisterRequest request) {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null registration request passed into RegisterService");
        }

        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            return new RegisterResponse("First name is required to register (please enter)");
        } else if (request.getLastName() == null || request.getLastName().isEmpty()) {
            return new RegisterResponse("Last name is required to register (please enter)");
        } else if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return new RegisterResponse("A Username is required to register (please enter)");
        } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return new RegisterResponse("A password is required to register (please enter)");
        }

        return serverFacade.register(request);
    }
}
