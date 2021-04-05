package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nathanielbennett.tweeter.model.service.response.PagedResponse;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.ResultsPage;
import com.nathanielbennett.tweeter.server.model.StoredFollowRelationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FollowDAO extends AmazonDAOTemplate {

    private static final String TABLE_NAME = "follow";

    private static final String PARTITION_KEY_LABEL = "followee";
    private static final String SORT_KEY_LABEL = "followed";

    @Override
    protected Object databaseItemToObject(Map<String, AttributeValue> item) throws DataAccessException {
        String followee = item.get(PARTITION_KEY_LABEL).getS();
        String followed = item.get(SORT_KEY_LABEL).getS();

        return new StoredFollowRelationship(followee, followed);
    }

    @Override
    protected Item objectToDatabaseItem(Object o) {
        StoredFollowRelationship followRelationship = (StoredFollowRelationship) o;

        return new Item()
                .withPrimaryKey(PARTITION_KEY_LABEL, followRelationship.getFollowee(), SORT_KEY_LABEL, followRelationship.getFollowed());
    }

    public FollowDAO() {
        super(TABLE_NAME, PARTITION_KEY_LABEL, SORT_KEY_LABEL);
    }

    public void addFollowRelationship(String followee, String followed) throws DataAccessException {
        addToTable(new StoredFollowRelationship(followee, followed));
    }

    public void removeFollowRelationship(String followee, String followed) throws DataAccessException {
        removeFromTable(followee, followed);
    }

    public boolean isFollowing(String followee, String followed) throws DataAccessException {
        return (getFromTable(followee, followed) != null);
    }

    public List<String> getFollowing(String followee, int limit, String lastFollowed) throws DataAccessException {
        ResultsPage resultsPage = getPagedFromDatabase(followee, limit, lastFollowed);

        List<String> following = new ArrayList<>();
        for (Object o : resultsPage.getValues()) {
            StoredFollowRelationship followRelationship = (StoredFollowRelationship) o;

            following.add(followRelationship.getFollowee());
        }

        return following;
    }


    public List<String> getFollowedBy(String followee, int limit, String lastFollowed) throws DataAccessException {

    }
}
