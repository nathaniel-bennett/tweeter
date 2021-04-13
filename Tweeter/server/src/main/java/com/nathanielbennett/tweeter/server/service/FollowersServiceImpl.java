package com.nathanielbennett.tweeter.server.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.FollowersService;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.server.dao.FollowDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.model.StoredUser;

import java.util.ArrayList;
import java.util.List;

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

        FollowDAO followDAO = getFollowDAO();
        UserDAO userDAO = getUserDAO();

        List<String> aliasesFollowedBy = followDAO.getFollowing(request.getFollowAlias(), request.getLimit(), request.getLastFollowAlias());
        boolean hasMorePages = (aliasesFollowedBy.size() == request.getLimit());

        List<User> usersFollowedBy = new ArrayList<>();
        for (String alias : aliasesFollowedBy) {
            StoredUser storedUser = userDAO.getUser(alias);
            if (storedUser != null) {
                usersFollowedBy.add(storedUser.toUser());
            }
        }

        return new FollowResponse(usersFollowedBy, hasMorePages);
    }

    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }
}
