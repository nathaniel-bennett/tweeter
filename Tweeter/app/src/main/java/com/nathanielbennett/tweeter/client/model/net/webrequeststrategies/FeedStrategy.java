package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class FeedStrategy implements ClientCommunicator.WebRequestStrategy {

    @Override
    public boolean hasRequestBody() {
        return false;
    }

    @Override
    public String getRequestPath(TweeterAPIRequest request) {

        StatusRequest statusRequest = (StatusRequest) request;

        String uri = "/feed?alias=" + statusRequest.getAlias() + "&limit=" + statusRequest.getLimit() + "&timestamp=";
        if (statusRequest.getTimestamp() != null && !statusRequest.getTimestamp().isEmpty()) {
            uri += statusRequest.getTimestamp();
        } else {
            uri += "%00";
        }

        return uri;
    }

    @Override
    public String getRequestMethod() {
        return "GET";
    }

    @Override
    public TweeterAPIResponse formResponse(String serializedResponse, int httpResponseCode) {
        Serializer serializer = new Serializer();
        StatusResponse response = serializer.deserialize(serializedResponse, StatusResponse.class);

        if (httpResponseCode != 200) {
            response.setSuccess(false);
        }

        return response;
    }
}
