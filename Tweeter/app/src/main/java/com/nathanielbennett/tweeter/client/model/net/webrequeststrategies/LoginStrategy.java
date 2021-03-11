package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class LoginStrategy implements ClientCommunicator.WebRequestStrategy{

    @Override
    public boolean hasRequestBody() {
        return true;
    }

    @Override
    public String getRequestPath(TweeterAPIRequest request) {
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
                return new LoginResponse("Bad Request: invalid form data in login");
            case 408:
                return new LoginResponse("Request timed out (please retry)");
            case 429:
                return new LoginResponse("Too many login requests received: try logging in later");
            case 500:
                return new LoginResponse("The server has encountered an unspecified error and is unable to log in");
            case 504:
                return new LoginResponse("Server timed out while processing request");
            default:
                return new LoginResponse("Unknown server error");
        }
    }
}
