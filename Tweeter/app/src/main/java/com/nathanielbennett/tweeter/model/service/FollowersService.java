package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

public class FollowersService extends Service {

    /**
     * Returns the users that the user specified in the request. Uses information in the request
     * object to limit the number of followers returned and to return the next set of followers after
     * any that w4ere returned in a previous request. Uses the {@link ServerFacade} to get the
     * followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public FollowResponse fetchFollowers(FollowRequest request) {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null Follow request passed into FollowersService");
        }

        if (request.getFollowAlias() == null || request.getFollowAlias().isEmpty()) {
            throw new NullPointerException("Alias field missing in FollowRequest (FollowersService)");
        }

        return serverFacade.getFollowers(request);
    }
}
