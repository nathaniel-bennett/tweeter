package com.nathanielbennett.tweeter.model.service.request;

public class StatusRequest implements TweeterAPIRequest {

    private String alias;
    private final int limit;
    private String lastStatusMessageSent;

    /**
     * Creates an instance.
     * @param userToGet the user that we want to get their stories.
     * @param limit
     */
    public StatusRequest(String userToGet, int limit, String lastStatusSent) {
        this.alias = userToGet;
        this.limit = limit;
        this.lastStatusMessageSent = lastStatusSent;
    }

    /**
     * Getter for userToGet.
     * @return userToGet
     */
    public String getAlias() {
        return alias;
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
