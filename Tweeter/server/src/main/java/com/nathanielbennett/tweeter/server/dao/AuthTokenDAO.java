package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessFailureException;

import java.util.List;


public class AuthTokenDAO {

    public AuthToken createAuthToken(String alias) throws DataAccessFailureException {
        AuthToken authToken = new AuthToken();

        // TODO: actually store auth token in database

        return authToken;
    }

    public List<AuthToken> getValidAuthTokens(String alias) throws DataAccessFailureException {
        return null;
    }


    public void deleteAuthToken(AuthToken authToken) throws DataAccessFailureException {

    }
}
