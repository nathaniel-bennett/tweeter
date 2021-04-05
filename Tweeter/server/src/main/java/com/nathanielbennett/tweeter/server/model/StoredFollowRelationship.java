package com.nathanielbennett.tweeter.server.model;

public class StoredFollowRelationship {

    private String followee;
    private String followed;

    public StoredFollowRelationship(String followee, String followed) {
        this.followee = followee;
        this.followed = followed;
    }

    public StoredFollowRelationship() { }



    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }
}
