package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;


public class PostService extends Service {

    /**
     * Returns the status for the user specified in the request. Uses information in the response
     * to determine if the pictures for the response need to be loaded.
     *
     * @param request the request to be made to the backend.
     * @return response from the backend.
     */
    public PostResponse addPost(PostRequest request) {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null status request passed into PostService");
        }

        if (request.getUser() == null) {
            throw new NullPointerException("User missing in post request (PostService)");
        }

        return serverFacade.addToStory(request);
    }
}