package com.nathanielbennett.tweeter.model.service.StatusService;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.util.ByteArrayUtils;

import java.io.IOException;

abstract public class TemplateStatusService {

    abstract public StatusResponse makeServerRequest(StatusRequest request) throws IOException;

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    protected void loadImages(StatusResponse response) throws IOException {
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
