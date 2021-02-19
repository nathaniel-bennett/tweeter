package com.nathanielbennett.tweeter.model.service.StatusService;

import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import java.io.IOException;

public class FeedService extends TemplateStatusService {

    /**
     * Returns the feed for the user specified in the request. Uses information in the response to
     * determine if the pictures for the response need to be loaded.
     *
     * @param request the request to be made to the backend.
     * @return response from the backend.
     * @throws IOException
     */
    @Override
    public StatusResponse makeServerRequest(StatusRequest request) throws IOException {
        StatusResponse response = getServerFacade().getFeed(request);

        if (response.isSuccess()) {
            loadImages(response);
        }

        return response;

    }
}
