package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public class LogoutRequest extends AuthorizedRequest {

    public LogoutRequest() {
    }

    public LogoutRequest(String username, AuthToken authToken) {
        super(username, authToken);
    }
}
