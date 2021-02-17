package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowersRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowersResponse;
import com.nathanielbennett.tweeter.util.ByteArrayUtils;

import java.io.IOException;

public class FollowersService {

    /**
     * Returns the users that the user specified in the request. Uses information in the request
     * object to limit the number of followers returned and to return the next set of followers after
     * any that w4ere returned in a previous request. Uses the {@link ServerFacade} to get the
     * followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public FollowersResponse getFollowers(FollowersRequest request) throws IOException {
        FollowersResponse response = getServerFacade().getFollowers(request);

        if (response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    /**
     * Loads the profile image data for each follower included in the response.
     *
     * @param response the response from the follower request.
     */
    private void loadImages(FollowersResponse response) throws IOException {
        for (User user: response.getFollowers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes.
     * @return
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
