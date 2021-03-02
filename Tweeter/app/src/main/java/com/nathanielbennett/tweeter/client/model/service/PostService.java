package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;

import java.io.IOException;


public class PostService extends Service {

    /**
     * Returns the status for the user specified in the request. Uses information in the response
     * to determine if the pictures for the response need to be loaded.
     *
     * @param request the request to be made to the backend.
     * @return response from the backend.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public PostResponse addPost(PostRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null status request passed into PostService");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new NullPointerException("Username missing in post request (PostService)");
        }

        if (request.getAuthToken() == null) {
            throw new NullPointerException("Auth token missing in post request (PostService)");
        }

        return serverFacade.addToStory(request);
    }
}