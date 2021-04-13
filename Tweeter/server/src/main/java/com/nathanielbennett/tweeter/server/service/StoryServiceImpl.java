package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.service.StoryService;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.dao.StoryDAO;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import java.util.List;

public class StoryServiceImpl extends AbstractStatusServiceTemplate implements StoryService {
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

        StoryDAO storyDAO = getStoryDAO();

        List<StoredStatus> storedStatusList = storyDAO.getUserStory(request.getAlias(), request.getLimit(), request.getTimestamp());
        boolean hasMorePages = (storedStatusList.size() == request.getLimit());

        List<Status> statuses = formUserStatuses(storedStatusList);

        return new StatusResponse(hasMorePages, statuses);
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
