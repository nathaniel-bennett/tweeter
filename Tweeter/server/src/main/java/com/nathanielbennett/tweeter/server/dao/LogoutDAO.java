package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.server.DataCache;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;

public class LogoutDAO {
    private static final DataCache dc = DataCache.getInstance();
    /**
     * Logs out the user specified in the request. The current implementation returns
     * generated data and doesn't actually make a network request.
     * @param logoutRequest Contains information about the user to be logged out.
     * @return A response indicating whether the logout was successful or not.
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        if (!logoutRequest.getAuthToken().equals(new AuthToken("Authorized"))) {
            throw new NotAuthorizedException("Invalid AuthToken");
        } else {
            return new LogoutResponse();
        }
    }
}
