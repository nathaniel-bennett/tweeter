package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;

import java.io.IOException;


public interface PostService {

    /**
     * Returns the status for the user specified in the request. Uses information in the response
     * to determine if the pictures for the response need to be loaded.
     *
     * @param request the request to be made to the backend.
     * @return response from the backend.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public PostResponse addPost(PostRequest request) throws IOException;
}