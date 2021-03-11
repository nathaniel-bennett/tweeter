package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class CheckFollowingStrategy implements ClientCommunicator.WebRequestStrategy {

    @Override
    public boolean hasRequestBody() {
        return false;
    }

    @Override
    public String getRequestPath(TweeterAPIRequest request) {
        CheckFollowingRequest followingRequest = (CheckFollowingRequest) request;
        return "/" + followingRequest.getUsername() + "/follows/" + followingRequest.getOtherUser();
    }

    @Override
    public String getRequestMethod() {
        return "GET";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse) {
        Serializer serializer = new Serializer();
        return serializer.deserialize(serializedResponse, CheckFollowingResponse.class);
    }

    @Override
    public TweeterAPIResponse formFailureResponse(int httpResponseCode) {
        switch (httpResponseCode) {
            case 400:
                return new CheckFollowingResponse("Client error");
            case 500:
                return new CheckFollowingResponse("Server error");
            default:
                return new CheckFollowingResponse("An unknown error occurred");
        }
    }
}
