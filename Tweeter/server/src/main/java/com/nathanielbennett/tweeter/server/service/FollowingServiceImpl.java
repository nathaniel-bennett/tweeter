package com.nathanielbennett.tweeter.server.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.FollowingService;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.server.dao.FollowDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.model.StoredUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceImpl  implements FollowingService {
    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    @Override
    public FollowResponse getFollowees(FollowRequest request) {

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

        List<String> aliasesFollowing = followDAO.getFollowedBy(request.getFollowAlias(), request.getLimit(), request.getLastFollowAlias());
        boolean hasMorePages = (aliasesFollowing.size() == request.getLimit());

        List<User> usersFollowing = new ArrayList<>();
        for (String alias : aliasesFollowing) {
            StoredUser storedUser = userDAO.getUser(alias);
            if (storedUser != null) {
                usersFollowing.add(storedUser.toUser());
            }
        }

        return new FollowResponse(usersFollowing, hasMorePages);
    }

    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
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
