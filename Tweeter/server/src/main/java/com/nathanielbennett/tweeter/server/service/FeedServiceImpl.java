package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.FeedService;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.dao.FeedDAO;

public class FeedServiceImpl implements FeedService {
    @Override
    public StatusResponse fetchFeed(StatusRequest request) {
        return null;
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
