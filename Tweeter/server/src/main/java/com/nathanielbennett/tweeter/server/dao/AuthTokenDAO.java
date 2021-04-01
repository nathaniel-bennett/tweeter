package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;

import java.util.List;


public class AuthTokenDAO {

    public AuthToken createAuthToken(String alias) throws DataAccessException {
        AuthToken authToken = new AuthToken();

        // TODO: actually store auth token in database

        return authToken;
    }

    public List<AuthToken> getValidAuthTokens(String alias) throws DataAccessException {
        return null;
    }


    public void deleteAuthToken(AuthToken authToken) throws DataAccessException {

    }
}
