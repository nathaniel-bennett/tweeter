package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.User;

public class StatusRequest implements TweeterAPIRequest {

    private User userToGet;
    private final int limit;
    private String lastStatusMessageSent;

    /**
     * Creates an instance.
     * @param userToGet the user that we want to get their stories.
     * @param limit
     */
    public StatusRequest(User userToGet, int limit, String lastStatusSent) {
        this.userToGet = userToGet;
        this.limit = limit;
        this.lastStatusMessageSent = lastStatusSent;
    }

    /**
     * Getter for userToGet.
     * @return userToGet
     */
    public User getUserToGet() {
        return userToGet;
    }

    /**
     * Getter for limit.
     * @return limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Getter for lastStatusMessageSent
     * @return lastStatusMessageSent
     */
    public String  getLastStatusSent() {
        return lastStatusMessageSent;
    }
}
