package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.AuthorizationService;
import com.nathanielbennett.tweeter.model.service.FollowService;
import com.nathanielbennett.tweeter.model.service.request.AuthorizationRequest;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.AuthorizationResponse;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
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

        // Now check to make sure username/auth token combo is valid

        AuthorizationRequest authRequest = new AuthorizationRequest(request);
        AuthorizationService authService = new AuthorizationServiceImpl();
        AuthorizationResponse authResponse = authService.isAuthorized(authRequest);

        if (!authResponse.getSuccess()) {
            return new FollowUserResponse(authResponse.getErrorMessage());
        }

        FollowDAO followDAO = getFollowDAO();
        followDAO.addFollowRelationship(request.getUserToFollow(), request.getUserToFollow());

        return new FollowUserResponse();
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

        // Now check to make sure username/auth token combo is valid
        AuthorizationRequest authRequest = new AuthorizationRequest(request);
        AuthorizationService authService = new AuthorizationServiceImpl();

        AuthorizationResponse authResponse = authService.isAuthorized(authRequest);
        if (!authResponse.getSuccess()) {
            return new UnfollowUserResponse(authResponse.getErrorMessage());
        }

        if (request.getUserToUnfollow() == null || request.getUserToUnfollow().isEmpty()) {
            throw new BadRequestException("Message body missing username to check");
        }

        FollowDAO followDAO = getFollowDAO();
        followDAO.removeFollowRelationship(request.getUsername(), request.getUserToUnfollow());

        return new UnfollowUserResponse();
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

        // Now check to make sure username/auth token combo is valid
        AuthorizationRequest authRequest = new AuthorizationRequest(request);
        AuthorizationService authService = new AuthorizationServiceImpl();

        AuthorizationResponse authResponse = authService.isAuthorized(authRequest);
        if (!authResponse.getSuccess()) {
            return new CheckFollowingResponse(authResponse.getErrorMessage());
        }

        FollowDAO followDAO = getFollowDAO();

        boolean isFollowing = followDAO.isFollowing(request.getUsername(), request.getOtherUser());
        return new CheckFollowingResponse(isFollowing);
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
