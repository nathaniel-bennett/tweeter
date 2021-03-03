package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.FollowingService;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

import java.io.IOException;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceProxy extends Service implements FollowingService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public FollowResponse getFollowees(FollowRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null Follow request passed into FollowingService");
        }

        if (request.getFollowAlias() == null || request.getFollowAlias().isEmpty()) {
            throw new NullPointerException("Alias field missing in FollowRequest (FollowingService)");
        }

        return serverFacade.getFollowing(request);
    }
}
