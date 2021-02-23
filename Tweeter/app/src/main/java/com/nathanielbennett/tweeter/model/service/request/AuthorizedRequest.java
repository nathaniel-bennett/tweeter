package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public abstract class AuthorizedRequest implements TweeterAPIRequest {
    private final String username;
    private final AuthToken authToken;

    public AuthorizedRequest(String username, AuthToken authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
