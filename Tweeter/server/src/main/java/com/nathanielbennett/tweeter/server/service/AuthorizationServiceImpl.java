package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.AuthorizationService;
import com.nathanielbennett.tweeter.model.service.request.AuthorizationRequest;
import com.nathanielbennett.tweeter.model.service.response.AuthorizationResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;

import java.util.List;

public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public AuthorizationResponse isAuthorized(AuthorizationRequest request) {
        AuthTokenDAO authTokenDAO = getAuthTokenDAO();

        List<AuthToken> validTokens = authTokenDAO.getValidAuthTokens(request.getUsername());

        for (AuthToken token : validTokens) {
            if (token.equals(request.getAuthToken())) {
                return new AuthorizationResponse();
            }
        }

        return new AuthorizationResponse("Invalid or expired auth token.");
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
