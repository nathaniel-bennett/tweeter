package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;

import java.io.IOException;

public interface RegisterService {

    /**
     * Attempts to register a new user with the given {@link RegisterRequest} information. Returns a
     * response containing the new user and its associated Auth Token on success, or an error
     * message on failure
     * @param request The user information used to register a new User.
     * @return A response indicating either success or failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public RegisterResponse register(RegisterRequest request) throws IOException;
}
