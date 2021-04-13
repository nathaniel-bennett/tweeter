package com.nathanielbennett.tweeter.model.service.request;

public class StatusRequest implements TweeterAPIRequest {

    private String lastTimestamp;
    private String alias;
    private int limit;

    /**
     * Creates an instance.
     * @param userToGet the user that we want to get their stories.
     * @param limit
     */
    public StatusRequest(String userToGet, int limit, String lastTimestamp) {
        this.alias = userToGet;
        this.limit = limit;
        this.lastTimestamp = lastTimestamp;
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
     * Getter for lastStatusTimestamp
     * @return lastStatusTimestamp
     */
    public String getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(String lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }
}
