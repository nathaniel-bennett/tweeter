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

    private static final String TABLE_NAME = "feed";

    private static final String PARTITION_KEY_LABEL = "owner";
    private static final String SORT_KEY_LABEL = "timestamp";
    private static final String ALIAS_LABEL = "alias";
    private static final String MESSAGE_LABEL = "message";


    public FeedDAO() {
        super(TABLE_NAME, PARTITION_KEY_LABEL, SORT_KEY_LABEL);
    }

    @Override
    protected Object databaseItemToObject(Map<String, AttributeValue> item) throws DataAccessException {
        // String feedOwner = item.get(PARTITION_KEY_LABEL).getS();
        String timestamp = item.get(SORT_KEY_LABEL).getS();
        String alias = item.get(ALIAS_LABEL).getS();
        String message = item.get(MESSAGE_LABEL).getS();

        return new StoredStatus(alias, message, timestamp);
    }

    @Override
    protected Item objectToDatabaseItem(Object o) {
        StoredStatus status = (StoredStatus) o;

        return new Item()
                .withPrimaryKey(PARTITION_KEY_LABEL, status.getFeedOwner(), SORT_KEY_LABEL, status.getTimestamp())
                .withString(ALIAS_LABEL, status.getAlias())
                .withString(MESSAGE_LABEL, status.getMessage());
    }


    public List<StoredStatus> getUserFeed(String feedOwner, int numStatuses, String lastStatusTimestamp) {
        ResultsPage resultsPage = getPagedFromDatabase(feedOwner, numStatuses, lastStatusTimestamp, false);
        if (!resultsPage.hasValues()) {
            return null;
        }

        List<StoredStatus> userFeed = new ArrayList<>();
        for (Object o : resultsPage.getValues()) {
            userFeed.add((StoredStatus) o);
        }


        return userFeed;
    }

//    public void addStatusToFeed(String feedOwner, StoredStatus status) {
//        status.setFeedOwner(feedOwner);
//
//        addToTable(status);
//    }

    public void addStatusToFeeds(List<String> feedOwners, StoredStatus status) {
        List<Object> statuses = new ArrayList<>();

        for (String feedOwner : feedOwners) {
            StoredStatus duplicateStatus = new StoredStatus(status.getAlias(), status.getMessage(), status.getTimestamp());
            duplicateStatus.setFeedOwner(feedOwner);

            statuses.add(duplicateStatus);
        }

        addToTableBatch(statuses);
    }

}
