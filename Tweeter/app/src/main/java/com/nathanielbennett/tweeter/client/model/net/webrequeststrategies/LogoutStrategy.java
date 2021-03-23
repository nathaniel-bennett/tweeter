package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class LogoutStrategy implements ClientCommunicator.WebRequestStrategy{

    @Override
    public boolean hasRequestBody() {
        return false;
    }

    @Override
    public String getRequestPath(TweeterAPIRequest request) {
        LogoutRequest logoutRequest = (LogoutRequest) request;

        return "/logout?username=" + logoutRequest.getUsername();
    }

    @Override
    public String getRequestMethod() {
        return "DELETE";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse, int httpResponseCode) {
        Serializer serializer = new Serializer();
        LogoutResponse response = serializer.deserialize(serializedResponse, LogoutResponse.class);

        if (httpResponseCode != 200) {
            response.setSuccess(false);
        }

        return response;
    }
}
