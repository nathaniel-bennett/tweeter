package com.nathanielbennett.tweeter.client.model.net.webrequeststrategies;

import android.util.Log;

import com.nathanielbennett.tweeter.client.model.net.ClientCommunicator;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;

public class StoryStrategy implements ClientCommunicator.WebRequestStrategy {

    @Override
    public boolean hasRequestBody() {
        return false;
    }

    @Override
    public String getRequestPath(TweeterAPIRequest request) {

        StatusRequest statusRequest = (StatusRequest) request;

        String uri = "/story?alias=" + statusRequest.getAlias() + "&limit=" + statusRequest.getLimit() + "&timestamp=";
        if (statusRequest.getTimestamp() != null && !statusRequest.getTimestamp().isEmpty()) {
            String timestamp = statusRequest.getTimestamp();

            int idx = timestamp.indexOf(":");
            while (idx >= 0) {
                timestamp = timestamp.substring(0, idx) + "%3A" + timestamp.substring(idx+1);
                idx = timestamp.indexOf(":");
            }

            uri += timestamp;
        } else {
            uri += "%00";
        }



        Log.i("StoryRequestURI", uri);

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
