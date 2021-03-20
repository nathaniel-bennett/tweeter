package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
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
        return "PUT";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse, int httpResponseCode) {
        Serializer serializer = new Serializer();
        RegisterResponse response = serializer.deserialize(serializedResponse, RegisterResponse.class);

        if (httpResponseCode != 200) {
            response.setSuccess(false);
        }

        return response;
    }
}
