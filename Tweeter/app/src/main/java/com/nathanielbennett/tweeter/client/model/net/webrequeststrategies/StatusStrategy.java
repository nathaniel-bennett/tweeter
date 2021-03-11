package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class StatusStrategy implements ClientCommunicator.WebRequestStrategy{

    @Override
    public boolean hasRequestBody() {
        return true;
    }

    @Override
    public String getRequestPath(TweeterAPIRequest request) {
        return "/status";
    }

    @Override
    public String getRequestMethod() {
        return "GET";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse) {
        Serializer serializer = new Serializer();
        return serializer.deserialize(serializedResponse, StatusResponse.class);
    }

    @Override
    public TweeterAPIResponse formFailureResponse(int httpResponseCode) {
        switch (httpResponseCode) {
            case 400:
                return new StatusResponse("Client error");
            case 500:
                return new StatusResponse("Server error");
            default:
                return new StatusResponse("An unknown error occurred");
        }
    }
}
