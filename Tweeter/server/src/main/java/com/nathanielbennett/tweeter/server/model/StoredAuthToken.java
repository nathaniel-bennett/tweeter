package com.nathanielbennett.tweeter.server.model;

public class StoredAuthToken {
    private String authTokenID;
    private String associatedUser;
    private String timestamp;

    public StoredAuthToken(String authTokenID, String associatedUser, String timestamp) {
        this.authTokenID = authTokenID;
        this.associatedUser = associatedUser;
        this.timestamp = timestamp;
    }

    public String getAuthTokenID() {
        return authTokenID;
    }

    public void setAuthTokenID(String authTokenID) {
        this.authTokenID = authTokenID;
    }

    public String getAssociatedUser() {
        return associatedUser;
    }

    public void setAssociatedUser(String associatedUser) {
        this.associatedUser = associatedUser;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
