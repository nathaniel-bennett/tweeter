package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.User;

public class PostRequest implements TweeterAPIRequest {
    private final String status;
    private final User user;
    // TODO: we need an Auth Token in this request...

    public PostRequest(String status, User user){
        this.status = status;
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}
