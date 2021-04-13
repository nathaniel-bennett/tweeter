package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.service.FeedService;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.dao.FeedDAO;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import java.util.List;

public class FeedServiceImpl extends AbstractStatusServiceTemplate implements FeedService {
    @Override
    public StatusResponse fetchFeed(StatusRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body missing or malformed");
        }

        if (request.getAlias() == null || request.getAlias().isEmpty()) {
            throw new BadRequestException("Request missing user");
        }

        List<StoredStatus> storedStatusList = getFeedDao().getUserFeed(request.getAlias(), request.getLimit(), request.getTimestamp());
        boolean hasMorePages = (storedStatusList == null) ? false : (storedStatusList.size() == request.getLimit());

        List<Status> statuses = (storedStatusList == null) ? null : formUserStatuses(storedStatusList);

        return new StatusResponse(hasMorePages, statuses);
    }

    /**
     * Returns an instance of {@link FeedDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public FeedDAO getFeedDao() {
        return new FeedDAO();
    }
}
