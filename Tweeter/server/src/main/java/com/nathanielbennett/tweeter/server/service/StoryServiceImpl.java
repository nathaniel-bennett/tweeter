package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.StoryService;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.dao.StoryDAO;

public class StoryServiceImpl implements StoryService {
    @Override
    public StatusResponse fetchStory(StatusRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body missing or malformed");
        }

        if (request.getAlias() == null || request.getAlias().isEmpty()) {
            throw new BadRequestException("Request missing alias");
        }

        if (request.getLimit() == 0) {
            throw new BadRequestException("Request limit cannot equal zero");
        }

        return getStoryDAO().getStory(request);
    }

    /**
     * Returns an instance of {@link StoryDAO}. Allows mocking of the StoryDAO class
     * for testing purposes. All usages of StoryDAO should get their StoryDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public StoryDAO getStoryDAO() {
        return new StoryDAO();
    }
}
