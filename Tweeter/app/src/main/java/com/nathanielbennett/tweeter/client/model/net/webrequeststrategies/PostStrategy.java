package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class PostStrategy implements ClientCommunicator.WebRequestStrategy{
    @Override
    public String getRequestPath() {
        return null;
    }

    @Override
    public String getRequestMethod() {
        return null;
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse) {
        return null;
    }

    @Override
    public TweeterAPIResponse formFailureResponse(int httpResponseCode) {
        return null;
    }
}
