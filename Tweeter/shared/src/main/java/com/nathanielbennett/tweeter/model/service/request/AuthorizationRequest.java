package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public class AuthorizationRequest extends AuthorizedRequest {

    public AuthorizationRequest(String username, AuthToken authToken) {
        super(username, authToken);
    }

    public AuthorizationRequest(AuthorizedRequest request) {
        super(request.getUsername(), request.getAuthToken());
    }

    public AuthorizationRequest() { }
}
