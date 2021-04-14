package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.StoredFollowRelationship;
import com.nathanielbennett.tweeter.server.model.StoredUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class UserDAO extends AmazonDAOTemplate{

    private static final String TABLE_NAME = "user";
    private static final String PARTITION_KEY_LABEL = "alias";

    private static final String FIRSTNAME_LABEL = "first_name";
    private static final String LASTNAME_LABEL = "last_name";
    private static final String IMAGEURL_LABEL = "imageURL";
    private static final String PASSWORD_LABEL = "password";
    private static final String FOLLOWERCOUNT_LABEL = "follower_count";
    private static final String FOLLOWEECOUNT_LABEL = "followee_count";

    public UserDAO() {
        super(TABLE_NAME, PARTITION_KEY_LABEL);
    }

    @Override
    protected Object databaseItemToObject(Map<String, AttributeValue> item) throws DataAccessException {
        String alias = item.get(PARTITION_KEY_LABEL).getS();

        String firstName = item.get(FIRSTNAME_LABEL).getS();
        String lastName = item.get(LASTNAME_LABEL).getS();
        String hashedPassword = item.get(PASSWORD_LABEL).getS();
        String imageURL = item.get(IMAGEURL_LABEL).getS();
        int follower_count = Integer.parseInt(item.get(FOLLOWERCOUNT_LABEL).getN());
        int followee_count = Integer.parseInt(item.get(FOLLOWEECOUNT_LABEL).getN());

        return new StoredUser(firstName, lastName, hashedPassword, alias, imageURL, follower_count, followee_count);
    }

    @Override
    protected Item objectToDatabaseItem(Object o) {
        StoredUser user = (StoredUser) o;

        return new Item()
                .withPrimaryKey(PARTITION_KEY_LABEL, user.getAlias())
                .withString(FIRSTNAME_LABEL, user.getFirstName())
                .withString(LASTNAME_LABEL, user.getLastName())
                .withString(PASSWORD_LABEL, user.getHashedPassword())
                .withString(IMAGEURL_LABEL, user.getImageUrl())
                .withInt(FOLLOWERCOUNT_LABEL, user.getFollowerCount())
                .withInt(FOLLOWEECOUNT_LABEL, user.getFolloweeCount());
    }

    public void createUser(StoredUser user) {
        addToTable(user);
    }

    public StoredUser getUser(String alias){
        return (StoredUser) getFromTable(alias);
    }

    public void decrementUserFollowing(String alias) {
        decrementTableField(alias, FOLLOWEECOUNT_LABEL);
    }

    public void incrementUserFollowing(String alias) {
        incrementTableField(alias, FOLLOWEECOUNT_LABEL);
    }

    public void decrementUserFollowers(String alias) {
        decrementTableField(alias, FOLLOWERCOUNT_LABEL);
    }

    public void incrementUserFollowers(String alias) {
        incrementTableField(alias, FOLLOWERCOUNT_LABEL);
    }

    public void addUserBatch(List<Object> users) {
        addToTableBatch(users);
    }
}
