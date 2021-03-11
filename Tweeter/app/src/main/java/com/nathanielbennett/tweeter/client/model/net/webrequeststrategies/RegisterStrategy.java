package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class RegisterStrategy implements ClientCommunicator.WebRequestStrategy{

    @Override
    public boolean hasRequestBody() {
        return true;
    }

    @Override
    public String getRequestPath(TweeterAPIRequest request) {
        return "/register";
    }

    @Override
    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse) {
        Serializer serializer = new Serializer();
        return serializer.deserialize(serializedResponse, RegisterResponse.class);
    }

    @Override
    public TweeterAPIResponse formFailureResponse(int httpResponseCode) {
        switch (httpResponseCode) {
            case 400:
                return new RegisterResponse("Client error");
            case 500:
                return new RegisterResponse("Server error");
            default:
                return new RegisterResponse("An unknown error occurred");
        }
    }
}
