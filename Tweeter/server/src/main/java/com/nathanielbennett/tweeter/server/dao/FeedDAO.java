package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.ResultsPage;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedDAO extends AmazonDAOTemplate {

    private static final String TABLE_NAME = "feeds";

    private static final String PARTITION_KEY_LABEL = "feed_owner";
    private static final String SORT_KEY_LABEL = "message_body";
    private static final String ALIAS_LABEL = "status_owner";
    private static final String TIMESTAMP_LABEL = "timestamp";


    public FeedDAO() {
        super(TABLE_NAME, PARTITION_KEY_LABEL, SORT_KEY_LABEL);
    }

    @Override
    protected Object databaseItemToObject(Map<String, AttributeValue> item) throws DataAccessException {
        // String feedOwner = item.get(PARTITION_KEY_LABEL).getS();
        String message = item.get(SORT_KEY_LABEL).getS();
        String alias = item.get(ALIAS_LABEL).getS();
        String timestamp = item.get(TIMESTAMP_LABEL).getS();

        return new StoredStatus(alias, message, timestamp);
    }

    @Override
    protected Item objectToDatabaseItem(Object o) {
        StoredStatus status = (StoredStatus) o;

        return new Item()
                .withPrimaryKey(PARTITION_KEY_LABEL, status.getFeedOwner(), SORT_KEY_LABEL, status.getMessage())
                .withString(ALIAS_LABEL, status.getAlias())
                .withString(TIMESTAMP_LABEL, status.getTimestamp());
    }


    public List<StoredStatus> getUserFeed(String feedOwner, int numStatuses, String lastSeenStatus) {
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

    public void addStatusToFeed(String feedOwner, StoredStatus status) {
        status.setFeedOwner(feedOwner);

        addToTable(status);
    }

    public void addStatusToFeeds(List<String> feedOwners, StoredStatus status) {
        List<StoredStatus> statuses = new ArrayList<>();

        for (String feedOwner : feedOwners) {
            StoredStatus duplicateStatus = new StoredStatus(status.getAlias(), status.getMessage(), status.getTimestamp());
            duplicateStatus.setFeedOwner(feedOwner);
        }

        addToTable(statuses);
    }

}
