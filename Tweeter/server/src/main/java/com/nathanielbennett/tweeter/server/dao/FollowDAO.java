package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import java.util.List;

public class FollowDAO {

    public void addFollowRelationship(String follower, String followee) throws DataAccessException {

    }

    public void removeFollowRelationship(String follower, String followee) throws DataAccessException {

    }

    public boolean isFollowing(String follower, String followee) throws DataAccessException {
        return false;
    }

    public List<String> getFollowing(String follower, int limit, String lastFollower) throws DataAccessException {
        return null;
    }

    public List<String> getFollowedBy(String followee, int limit, String lastfollowee) throws DataAccessException {
        return null;
    }
}
