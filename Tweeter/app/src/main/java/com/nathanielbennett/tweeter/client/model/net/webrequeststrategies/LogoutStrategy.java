package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class LogoutStrategy implements ClientCommunicator.WebRequestStrategy{

    @Override
    public boolean hasRequestBody() {
        return true;
    }

    @Override
    public String getRequestPath(TweeterAPIRequest request) {
        return "/logout";
    }

    @Override
    public String getRequestMethod() {
        return "DELETE";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse) {
        Serializer serializer = new Serializer();
        return serializer.deserialize(serializedResponse, LogoutResponse.class);
    }

    @Override
    public TweeterAPIResponse formFailureResponse(int httpResponseCode) {
        switch (httpResponseCode) {
            case 400:
                return new LogoutResponse("Client error");
            case 500:
                return new LogoutResponse("Server error");
            default:
                return new LogoutResponse("An unknown error occurred");
        }
    }
}
