package com.nathanielbennett.tweeter.model.service.response;

public class CheckFollowingResponse extends TweeterAPIResponse {
    Boolean followingUser;

    public CheckFollowingResponse(boolean isFollowing) {
        super();
        this.followingUser = isFollowing;
    }

    public CheckFollowingResponse(String errorMessage) {
        super(errorMessage);
        this.followingUser = null;
    }

    public CheckFollowingResponse() { }

    public Boolean getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(Boolean following) {
        followingUser = following;
    }
}
