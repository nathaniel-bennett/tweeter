package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessFailureException;
import com.nathanielbennett.tweeter.server.model.ResultsPage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class AuthTokenDAO extends AmazonDAOTemplate {

    //TODO: MAKE STATIC VARIABLES FOR TABLEnAME, POSITIONKEYATTR, SORTKEYATTR
    private static final String TABLE_NAME = "auth_token";
    private static final String PARTITION_KEY_LABEL = "username";
    private static final String SORT_KEY_LABEL = "auth_token";

    public AuthTokenDAO() { super(TABLE_NAME, PARTITION_KEY_LABEL, SORT_KEY_LABEL); }

    /*
    public AuthToken createAuthToken(String alias) throws DataAccessFailureException {
        AuthToken authToken = new AuthToken();

        // TODO: actually store auth token in database
        // TODO: NO

        try {
            table.putItem(new Item().withPrimaryKey("username", alias,
                    "auth_token", authToken.getAuthTokenID()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessFailureException("[InternalServerError] " + e.getMessage());
        }

        return authToken;
    }

    public List<AuthToken> getValidAuthTokens(String alias) throws DataAccessFailureException {

        List<AuthToken> authTokens = new ArrayList<>();

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("username = " + alias)
                .withScanIndexForward(true);

        ItemCollection<QueryOutcome> items;
        Iterator<Item> itemIterator;
        Item item;

        try {
            items = table.query(querySpec);
            itemIterator = items.iterator();
            while (itemIterator.hasNext()) {
                item = itemIterator.next();
                AuthToken authToken = new AuthToken(item.getString("auth_token"));
                authTokens.add(authToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessFailureException("[InternalServerError] Failure to access auth DAO " + e.getMessage());
        }

        return authTokens;
    }


    public void deleteAuthToken(AuthToken authToken) throws DataAccessFailureException {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withConditionExpression("auth_token = " + authToken.getAuthTokenID());

        try {
            table.deleteItem(deleteItemSpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessFailureException("[InternalServerError] unable to delete auth token: " + e.getMessage());
        }
    }

     */

    @Override
    protected Object databaseItemToObject(Map<String, AttributeValue> item) throws DataAccessException {

        String authTokenID = item.get(SORT_KEY_LABEL).getS();
        return new AuthToken(authTokenID);
    }

    @Override
    protected Item objectToDatabaseItem(Object o) {
        AuthToken authToken = (AuthToken) o;

        return new Item()
                .withPrimaryKey(PARTITION_KEY_LABEL, authToken.getAssociatedUser(),
                        SORT_KEY_LABEL, authToken.getAuthTokenID());
    }

    public AuthToken createToken(String userAlias) {
        AuthToken authToken = new AuthToken();
        authToken.setAssociatedUser(userAlias);

        addToTable(authToken);

        return authToken;

    }

    public boolean checkToken(AuthToken authToken) {
        ResultsPage resultsPage = getPagedFromDatabase(authToken.getAssociatedUser(), 10, null);

        if (resultsPage.hasValues()) {
            for (Object o : resultsPage.getValues()) {
                if (authToken.equals(o)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void deleteToken(AuthToken authToken) {
        removeFromTable(authToken.getAssociatedUser(), authToken.getAuthTokenID());
    }
}
