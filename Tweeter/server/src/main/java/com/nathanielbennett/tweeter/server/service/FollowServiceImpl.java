package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.FollowService;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;
import com.nathanielbennett.tweeter.server.dao.FollowDAO;

public class FollowServiceImpl implements FollowService {

    @Override
    public FollowUserResponse follow(FollowUserRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body missing or malformed");
        }

        if (request.getAuthToken() == null || request.getAuthToken().getAuthTokenID().isEmpty()) {
            throw new NotAuthorizedException("Authorization Token not included in request header");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Message body missing user's username");
        }

        if (request.getUserToFollow() == null || request.getUserToFollow().isEmpty()) {
            throw new BadRequestException("Message body missing username to check");
        }

        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        if (!authTokenDAO.checkToken(request.getAuthToken())) {
            // TODO: something if the token isn't valid
        }

        return getFollowDAO().follow(request);
    }

    @Override
    public UnfollowUserResponse unfollow(UnfollowUserRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body missing or malformed");
        }

        if (request.getAuthToken() == null || request.getAuthToken().getAuthTokenID().isEmpty()) {
            throw new NotAuthorizedException("Authorization Token not included in request header");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Message body missing user's username");
        }

        if (request.getUserToUnfollow() == null || request.getUserToUnfollow().isEmpty()) {
            throw new BadRequestException("Message body missing username to check");
        }

        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        if (!authTokenDAO.checkToken(request.getAuthToken())) {
            // TODO: something if the token isn't valid
        }

        return getFollowDAO().unfollow(request);
    }

    @Override
    public CheckFollowingResponse checkFollowStatus(CheckFollowingRequest request) {
        if (request == null) {
            throw new BadRequestException("Message body missing or malformed");
        }

        if (request.getAuthToken() == null || request.getAuthToken().getAuthTokenID().isEmpty()) {
            throw new NotAuthorizedException("Authorization Token not included in request header");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Message body missing user's username");
        }

        if (request.getOtherUser() == null || request.getOtherUser().isEmpty()) {
            throw new BadRequestException("Message body missing username to check");
        }

        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        if (!authTokenDAO.checkToken(request.getAuthToken())) {
            // TODO: something if the token isn't valid
        }

        return getFollowDAO().isFollowing(request);
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
}
