package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public class CheckFollowingRequest implements TweeterAPIRequest {
    private String username;
    private AuthToken authToken;
    private String otherUser;


    public CheckFollowingRequest(String username, AuthToken authToken, String otherUser) {
        this.username = username;
        this.authToken = authToken;
        this.otherUser = otherUser;
    }

    public String getUsername() {
        return username;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public String getOtherUser() {
        return otherUser;
    }
}
