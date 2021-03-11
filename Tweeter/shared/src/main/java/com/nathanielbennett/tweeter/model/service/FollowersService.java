package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

import java.io.IOException;

public interface FollowersService {

    /**
     * Returns the users that the user specified in the request. Uses information in the request
     * object to limit the number of followers returned and to return the next set of followers after
     * any that w4ere returned in a previous request. Uses the {@link ServerFacade} to get the
     * followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public FollowResponse fetchFollowers(FollowRequest request) throws IOException;
}
