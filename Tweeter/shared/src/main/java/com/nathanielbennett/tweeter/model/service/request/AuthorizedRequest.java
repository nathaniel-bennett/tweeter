package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public abstract class AuthorizedRequest implements TweeterAPIRequest {

    private String username;
    private AuthToken authToken;

    public AuthorizedRequest(String username, AuthToken authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public AuthorizedRequest() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
