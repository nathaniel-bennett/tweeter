package com.nathanielbennett.tweeter.model.service.response;

public class CheckFollowingResponse extends TweeterAPIResponse {
    Boolean isFollowing;

    public CheckFollowingResponse(boolean isFollowing) {
        super();
        this.isFollowing = isFollowing;
    }

    public CheckFollowingResponse(String errorMessage) {
        super(errorMessage);
        this.isFollowing = null;
    }

    public Boolean isFollowingUser() {
        return isFollowing;
    }

}
