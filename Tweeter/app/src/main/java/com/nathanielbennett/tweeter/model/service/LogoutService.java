package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;


/**
 * Contains the business logic to support the logout operation.
 */
public class LogoutService extends Service {

    /**
     * Attempts to log a user out given the information in the passed in {@link LogoutRequest}.
     * Returns a response that either indicates success or reports failure with a reason message.
     * @param request The information of the user being logged out.
     * @return A response indicating either success or failure.
     */
    public LogoutResponse logout(LogoutRequest request) {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null logout request passed into LogoutService");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return new LogoutResponse("Username required to log out of session");
        } else if (request.getAuthToken() == null) {
            return new LogoutResponse("Auth token required to log out of session");
        }

        return serverFacade.logout(request);
    }
}
