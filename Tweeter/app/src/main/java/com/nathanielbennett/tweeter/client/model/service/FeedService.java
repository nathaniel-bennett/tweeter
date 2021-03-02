package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import java.io.IOException;


public class FeedService extends Service {

    /**
     * Returns the feed for the user specified in the request. Uses information in the response to
     * determine if the pictures for the response need to be loaded.
     *
     * @param request the request to be made to the backend.
     * @return response from the backend.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public StatusResponse fetchFeed(StatusRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null status request passed into FeedService");
        }

        if (request.getUserToGet() == null) {
            throw new NullPointerException("User field missing in status request (FeedService)");
        }

        return serverFacade.getFeed(request);
    }
}
