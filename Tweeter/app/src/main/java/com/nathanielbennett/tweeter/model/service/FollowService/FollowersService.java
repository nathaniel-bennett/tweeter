package com.nathanielbennett.tweeter.model.service.FollowService;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

import java.io.IOException;

public class FollowersService extends TemplateFollowService {

    /**
     * Returns the users that the user specified in the request. Uses information in the request
     * object to limit the number of followers returned and to return the next set of followers after
     * any that w4ere returned in a previous request. Uses the {@link ServerFacade} to get the
     * followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    @Override
    public FollowResponse makeServerRequest(FollowRequest request) throws IOException {
        FollowResponse response = getServerFacade().getFollowers(request);

        if (response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

}
