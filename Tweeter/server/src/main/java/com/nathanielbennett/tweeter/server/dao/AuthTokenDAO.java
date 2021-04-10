package com.nathanielbennett.tweeter.server.dao;


import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.StoredAuthToken;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


public class AuthTokenDAO extends AmazonDAOTemplate {

    private static final String TABLE_NAME = "auth_token";
    private static final String PARTITION_KEY_LABEL = "username";
    private static final String SORT_KEY_LABEL = "auth_token";
    private static final String TIMESTAMP_LABEL = "timestamp";

    public AuthTokenDAO() { super(TABLE_NAME, PARTITION_KEY_LABEL, SORT_KEY_LABEL); }

    @Override
    protected Object databaseItemToObject(Map<String, AttributeValue> item) throws DataAccessException {

        String authTokenID = item.get(SORT_KEY_LABEL).getS();
        String associatedUser = item.get(PARTITION_KEY_LABEL).getS();
        String timestamp = item.get(TIMESTAMP_LABEL).getS();

        return new StoredAuthToken(authTokenID, associatedUser, timestamp);
    }

    @Override
    protected Item objectToDatabaseItem(Object o) {
        StoredAuthToken authToken = (StoredAuthToken) o;

        return new Item()
                .withPrimaryKey(PARTITION_KEY_LABEL, authToken.getAssociatedUser(),
                        SORT_KEY_LABEL, authToken.getAuthTokenID())
                .withString(TIMESTAMP_LABEL, authToken.getTimestamp());
    }

    public AuthToken createToken(String userAlias) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

        AuthToken authToken = new AuthToken();
        StoredAuthToken storedAuthToken = new StoredAuthToken(authToken.getAuthTokenID(), userAlias, timestamp);

        addToTable(storedAuthToken);

        return authToken;
    }

    public boolean checkToken(AuthToken authToken, String associatedUser) {

        List<Object> oValues = getAllFromTable(associatedUser);

        for (Object value : oValues) {
            StoredAuthToken storedAuthToken = (StoredAuthToken) value;

            LocalDateTime authTokenStart = LocalDateTime.parse(storedAuthToken.getTimestamp());
            LocalDateTime authTokenEnd = authTokenStart.plusHours(1);

            if (LocalDateTime.now().isAfter(authTokenEnd)) {
                deleteToken(storedAuthToken.getAuthTokenID(), storedAuthToken.getAssociatedUser());
                continue;
            }

            if (authToken.getAuthTokenID().equals(storedAuthToken.getAuthTokenID())) {
                return true;
            }
        }

        return false;
    }

    public void deleteToken(String authTokenID, String associatedUser) {
        removeFromTable(associatedUser, authTokenID);
    }
}
