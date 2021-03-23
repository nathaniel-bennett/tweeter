package com.nathanielbennett.tweeter.model.service.request;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class FollowRequest implements TweeterAPIRequest {

    private String followAlias;
    private int limit;
    private String lastFollowAlias;

    /**
     * Creates an instance.
     *
     * @param followAlias the alias of the user whose follow data is to be returned.
     * @param limit the maximum number of followUsers to return.
     * @param lastFollowAlias the alias of the last follow data that was returned in the previous request (null if
     *                     there was no previous request or if no follow data was returned in the
     *                     previous request).
     */
    public FollowRequest(String followAlias, int limit, String lastFollowAlias) {
        this.followAlias = followAlias;
        this.limit = limit;
        this.lastFollowAlias = lastFollowAlias;
    }

    public FollowRequest() { }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public String getFollowAlias() {
        return followAlias;
    }

    public void setFollowAlias(String followAlias) {
        this.followAlias = followAlias;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public String getLastFollowAlias() {
        return lastFollowAlias;
    }

    public void setLastFollowAlias(String lastFollowAlias) {
        this.lastFollowAlias = lastFollowAlias;
    }
}
