package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class LoginStrategy implements ClientCommunicator.WebRequestStrategy{
    @Override
    public String getRequestPath() {
        return "/login";
    }

    @Override
    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse) {
        Serializer serializer = new Serializer();
        return serializer.deserialize(serializedResponse, LoginResponse.class);
    }

    @Override
    public TweeterAPIResponse formFailureResponse(int httpResponseCode) {
        switch (httpResponseCode) {
            case 400:
                return new LoginResponse("Client error thingy");
            case 500:
                return new LoginResponse("Server error thingy");
            default:
                return new LoginResponse("Unknown server error");
        }
    }
}
