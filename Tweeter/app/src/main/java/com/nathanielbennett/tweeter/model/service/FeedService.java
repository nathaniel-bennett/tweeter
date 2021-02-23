package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;


public class FeedService extends Service {

    /**
     * Returns the feed for the user specified in the request. Uses information in the response to
     * determine if the pictures for the response need to be loaded.
     *
     * @param request the request to be made to the backend.
     * @return response from the backend.
     */
    public StatusResponse fetchFeed(StatusRequest request) {
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
