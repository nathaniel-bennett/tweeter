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
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessFailureException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AuthTokenDAO {


    DynamoDB db;
    Table table;

    public AuthTokenDAO() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-west-2")
                .build();
        this.db = new DynamoDB(client);

        this.table = db.getTable("auth_token");
    }

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
}
