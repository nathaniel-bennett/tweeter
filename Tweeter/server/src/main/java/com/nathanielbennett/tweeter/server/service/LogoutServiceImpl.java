package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.LogoutService;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;
import com.nathanielbennett.tweeter.server.dao.LogoutDAO;

public class LogoutServiceImpl implements LogoutService {
    @Override
    public LogoutResponse logout(LogoutRequest request) {
        if (request == null) {
            throw new BadRequestException("Message body missing or malformed");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Logout request missing username");
        }

        if (request.getAuthToken() == null || request.getAuthToken().getAuthTokenID().isEmpty()) {
            throw new NotAuthorizedException("Logout request missing Authorization Token");
        }

        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        authTokenDAO.deleteToken(request.getAuthToken());

        return getLogoutDao().logout(request);
    }

    /**
     * Returns an instance of {@link LogoutDAO}. Allows mocking of the LogoutDAO class
     * for testing purposes. All usages of LogoutDAO should get their LogoutDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public LogoutDAO getLogoutDao() {
        return new LogoutDAO();
    }
}
