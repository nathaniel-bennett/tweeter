package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.ResultsPage;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoryDAO extends AmazonDAOTemplate {

    private static final String TABLE_NAME = "story";

    private static final String PARTITION_KEY_LABEL = "story_owner";
    private static final String SORT_KEY_LABEL = "message_body";
    private static final String TIMESTAMP_LABEL = "timestamp";

    public StoryDAO() {
        super(TABLE_NAME, PARTITION_KEY_LABEL, SORT_KEY_LABEL);
    }


    @Override
    protected Object databaseItemToObject(Map<String, AttributeValue> item) throws DataAccessException {
        String alias = item.get(PARTITION_KEY_LABEL).getS();
        String message = item.get(SORT_KEY_LABEL).getS();
        String timestamp = item.get(TIMESTAMP_LABEL).getS();

        return new StoredStatus(alias, message, timestamp);
    }

    @Override
    protected Item objectToDatabaseItem(Object o) {
        StoredStatus status = (StoredStatus) o;

        return new Item()
                .withPrimaryKey(PARTITION_KEY_LABEL, status.getAlias(), SORT_KEY_LABEL, status.getMessage())
                .withString(TIMESTAMP_LABEL, status.getTimestamp());
    }

    public List<StoredStatus> getUserStory(String feedOwner, int numStatuses, String lastSeenStatus) {
        ResultsPage resultsPage = getPagedFromDatabase(feedOwner, numStatuses, lastSeenStatus);
        if (!resultsPage.hasValues()) {
            return null;
        }

        List<StoredStatus> userFeed = new ArrayList<>();
        for (Object o : resultsPage.getValues()) {
            userFeed.add((StoredStatus) o);
        }


        return userFeed;
    }

    public void addStatusToStory(StoredStatus status) {
        addToTable(status);
    }
}
