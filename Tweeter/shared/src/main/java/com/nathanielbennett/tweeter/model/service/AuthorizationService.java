package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.service.request.AuthorizationRequest;
import com.nathanielbennett.tweeter.model.service.response.AuthorizationResponse;

public interface AuthorizationService {
    public AuthorizationResponse isAuthorized(AuthorizationRequest request);
}
