package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public class PostRequest extends AuthorizedRequest {

    private String status;

    public PostRequest(String status, String username, AuthToken authToken) {
        super(username, authToken);
        this.status = status;
    }

    public PostRequest() { }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
