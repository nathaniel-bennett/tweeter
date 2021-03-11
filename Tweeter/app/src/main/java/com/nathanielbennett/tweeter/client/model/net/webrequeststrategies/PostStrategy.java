package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class PostStrategy implements ClientCommunicator.WebRequestStrategy{
    @Override
    public String getRequestPath() {
        return "/post";
    }

    @Override
    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse) {
        Serializer serializer = new Serializer();
        return serializer.deserialize(serializedResponse, PostResponse.class);
    }

    @Override
    public TweeterAPIResponse formFailureResponse(int httpResponseCode) {
        switch (httpResponseCode) {
            case 400:
                return new PostResponse("Client error");
            case 500:
                return new PostResponse("Server error");
            default:
                return new PostResponse("An unknown error occurred");
        }
    }
}
