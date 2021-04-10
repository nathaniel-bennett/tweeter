package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.AuthorizationService;
import com.nathanielbennett.tweeter.model.service.request.AuthorizationRequest;
import com.nathanielbennett.tweeter.model.service.response.AuthorizationResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;

public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public AuthorizationResponse isAuthorized(AuthorizationRequest request) {
        AuthTokenDAO authTokenDAO = getAuthTokenDAO();

        if (authTokenDAO.checkToken(request.getAuthToken(), request.getUsername())) {
            return new AuthorizationResponse();
        } else {
            return new AuthorizationResponse("Invalid or expired auth token.");
        }
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
