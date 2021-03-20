package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.FollowersService;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.dao.FollowersDAO;

public class FollowersServiceImpl implements FollowersService {
    @Override
    public FollowResponse fetchFollowers(FollowRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body missing or malformed");
        }

        if (request.getFollowAlias() == null || request.getFollowAlias().isEmpty()) {
            throw new BadRequestException("Follow handle missing from request");
        }

        if (request.getLimit() <= 0) {
            throw new BadRequestException("Bad limit on number of Followers to return");
        }
        return getFollowersDAO().getFollowers(request);
    }

    /**
     * Returns an instance of {@link FollowersDAO}. Allows mocking of the FollowersDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowersDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public FollowersDAO getFollowersDAO() {
        return new FollowersDAO();
    }
}
