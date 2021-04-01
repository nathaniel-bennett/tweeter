package com.nathanielbennett.tweeter.server.model;

public class StoredStatus {
    private String feedOwner = null; // Only used in DAO
    private String alias;
    private String message;
    private String timestamp;

    public StoredStatus(String alias, String message, String timestamp) {
        this.alias = alias;
        this.message = message;
        this.timestamp = timestamp;
    }

    public StoredStatus() { }


    public String getFeedOwner() {
        return feedOwner;
    }

    public void setFeedOwner(String feedOwner) {
        this.feedOwner = feedOwner;
    }


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
