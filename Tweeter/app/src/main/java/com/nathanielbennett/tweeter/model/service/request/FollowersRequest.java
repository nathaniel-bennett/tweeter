package com.nathanielbennett.tweeter.model.service.request;

public class FollowersRequest implements Request {

    private final String followeeAlias;
    private final int limit;
    private final String lastFollowerAlias;

    /**
     * Creates an instance.
     *
     * @param followeeAlias the alias of the user whose followers are to be returned.
     * @param limit the maximum number of followers to return.
     * @param lastFollowerAlias the alias of the last follower that was returned in the previous request (null
     *                          if there was no previous request or if no followers were returned in
     *                          the previous request).
     */
    public FollowersRequest(String followeeAlias, int limit, String lastFollowerAlias) {
        this.followeeAlias = followeeAlias;
        this.limit = limit;
        this.lastFollowerAlias = lastFollowerAlias;
    }

    /**
     * Returns the followee whose followers are to be returned by this request.
     * @return
     */
    public String getFolloweeAlias() {
        return followeeAlias;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last follower that was returned in the previous request or null if there was no
     * previous request or if no followers were returned in the previous request.
     *
     * @return the last follower.
     */
    public String getLastFollowerAlias() {
        return lastFollowerAlias;
    }
}
