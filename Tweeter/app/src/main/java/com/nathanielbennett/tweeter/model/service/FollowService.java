package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

import java.io.IOException;

public class FollowService extends Service {

    /**
     * Attempts to assign one user as following another user.
     *
     * @param request contains the data required to fulfill the request.
     * @return a response indicating either success or failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public FollowUserResponse follow(FollowUserRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null FollowUser request passed into FollowService");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new NullPointerException("username missing from FollowUser request (FollowService)");
        }

        if (request.getAuthToken() == null) {
            throw new NullPointerException("auth token missing from FollowUser request (FollowService)");
        }

        if (request.getUserToFollow() == null || request.getUserToFollow().isEmpty()) {
            throw new NullPointerException("userToFollow username missing from FollowUser request (FollowService)");
        }

        if (request.getUserToFollow().equals(request.getUsername())) {
            return new FollowUserResponse("Error - too much vanity (you can't follow yourself)");
        }

        return serverFacade.follow(request);
    }


    /**
     * @param request contains the data required to fulfill the request.
     * @return a response indicating either success or failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public UnfollowUserResponse unfollow(UnfollowUserRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null UnfollowUser request passed into FollowService");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new NullPointerException("username missing from UnfollowUser request (FollowService)");
        }

        if (request.getAuthToken() == null) {
            throw new NullPointerException("auth token missing from UnfollowUser request (FollowService)");
        }

        if (request.getUserToUnfollow() == null || request.getUserToUnfollow().isEmpty()) {
            throw new NullPointerException("userToUnfollow username missing from UnfollowUser request (FollowService)");
        }

        return serverFacade.unfollow(request);
    }


    /**
     * @param request A request containing information on the logged in user and another user.
     * @return A response indicating whether the user is followed or not on success, or an error
     * message on failure
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public CheckFollowingResponse checkFollowStatus(CheckFollowingRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();

        if (request == null) {
            throw new NullPointerException("Null IsFollowingRequest passed into FollowService");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new NullPointerException("Username field of IsFollowingRequest not filled (FollowService)");
        }

        if (request.getOtherUser() == null || request.getOtherUser().isEmpty()) {
            throw new NullPointerException("Other User field of IsFollowingRequest not filled (FollowService)");
        }

        if (request.getAuthToken() == null) {
            throw new NullPointerException("Auth Token field of IsFollowingRequest not filled (FollowService");
        }

        return serverFacade.isFollowing(request);
    }

}
