package com.nathanielbennett.tweeter.model.service.FollowService;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

import java.io.IOException;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingService extends TemplateFollowService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    @Override
    public FollowResponse makeServerRequest(FollowRequest request) throws IOException {
        FollowResponse response = getServerFacade().getFollowees(request);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }
}
