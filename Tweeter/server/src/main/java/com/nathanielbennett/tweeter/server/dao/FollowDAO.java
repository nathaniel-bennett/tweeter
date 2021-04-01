package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.server.exceptions.DataAccessFailureException;
import java.util.List;

public class FollowDAO {

    public void addFollowRelationship(String follower, String followee) throws DataAccessFailureException {

    }

    public void removeFollowRelationship(String follower, String followee) throws DataAccessFailureException {

    }

    public boolean isFollowing(String follower, String followee) throws DataAccessFailureException {
        return false;
    }

    public List<String> getFollowing(String follower) throws DataAccessFailureException {
        return null;
    }

    public List<String> getFollowedBy(String followee) throws DataAccessFailureException {
        return null;
    }
}
