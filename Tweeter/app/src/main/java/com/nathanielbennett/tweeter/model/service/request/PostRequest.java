package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.User;

public class PostRequest implements TweeterAPIRequest {
    private String status;
    private User user;

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
