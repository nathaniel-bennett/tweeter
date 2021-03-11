package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import java.io.IOException;


public interface FeedService {

    /**
     * Returns the feed for the user specified in the request. Uses information in the response to
     * determine if the pictures for the response need to be loaded.
     *
     * @param request the request to be made to the backend.
     * @return response from the backend.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public StatusResponse fetchFeed(StatusRequest request) throws IOException;
}
