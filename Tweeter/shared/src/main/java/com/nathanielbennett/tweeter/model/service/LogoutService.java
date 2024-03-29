package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;

import java.io.IOException;


/**
 * Contains the business logic to support the logout operation.
 */
public interface LogoutService {

    /**
     * Attempts to log a user out given the information in the passed in {@link LogoutRequest}.
     * Returns a response that either indicates success or reports failure with a reason message.
     *
     * @param request The information of the user being logged out.
     * @return A response indicating either success or failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public LogoutResponse logout(LogoutRequest request) throws IOException;
}
