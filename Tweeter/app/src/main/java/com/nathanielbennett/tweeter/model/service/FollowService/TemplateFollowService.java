package com.nathanielbennett.tweeter.model.service.FollowService;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.util.ByteArrayUtils;

import java.io.IOException;

abstract public class TemplateFollowService {


    abstract public FollowResponse makeServerRequest(FollowRequest request) throws IOException;

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    protected void loadImages(FollowResponse response) throws IOException {
        for(User user : response.getRequestedUsers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
