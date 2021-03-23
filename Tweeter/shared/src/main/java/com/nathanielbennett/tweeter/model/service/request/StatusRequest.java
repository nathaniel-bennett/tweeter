package com.nathanielbennett.tweeter.model.service.request;

public class StatusRequest implements TweeterAPIRequest {

    private String lastStatusMessageSent;
    private String alias;
    private int limit;

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

    public StatusRequest() { }

    /**
     * Getter for userToGet.
     * @return userToGet
     */
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Getter for limit.
     * @return limit
     */
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Getter for lastStatusMessageSent
     * @return lastStatusMessageSent
     */
    public String getLastStatus() {
        return lastStatusMessageSent;
    }

    public void setLastStatus(String lastStatusMessageSent) {
        this.lastStatusMessageSent = lastStatusMessageSent;
    }
}
