package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public class CheckFollowingRequest extends AuthorizedRequest {
    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    private String otherUser;


    public CheckFollowingRequest(String username, AuthToken authToken, String otherUser) {
        super(username, authToken);
        this.otherUser = otherUser;
    }

    public String getOtherUser() {
        return otherUser;
    }
}
