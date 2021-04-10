package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.LogoutService;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;

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
        authTokenDAO.deleteToken(request.getAuthToken().getAuthTokenID(), request.getUsername()); // TODO: notify when nothing was deleted

        return new LogoutResponse();
    }


    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
