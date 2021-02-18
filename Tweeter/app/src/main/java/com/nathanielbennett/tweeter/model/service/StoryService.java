package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.StoryRequest;
import com.nathanielbennett.tweeter.model.service.response.StoryResponse;
import com.nathanielbennett.tweeter.util.ByteArrayUtils;

import java.io.IOException;

public class StoryService {

    /**
     * Returns the status for the user specified in the request. Uses information in the response
     * to determine if the pictures for the response need to be loaded.
     * @param request
     * @return
     * @throws IOException
     */
    public StoryResponse makeServerRequest(StoryRequest request) throws IOException {
        StoryResponse response = getServerFacade().getStory(request);

        if (response.isSuccess()) {
            loadImages(response);
        }

        return response;

    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    private void loadImages(StoryResponse response) throws IOException {
        for (Status status : response.getStatuses()) {
            User statusUser = status.getUserOfStatus();
            byte [] bytes = ByteArrayUtils.bytesFromUrl(statusUser.getImageUrl());
            statusUser.setImageBytes(bytes);
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
