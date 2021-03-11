package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

import java.io.IOException;

public interface FollowService {

    /**
     * Attempts to assign one user as following another user.
     *
     * @param request contains the data required to fulfill the request.
     * @return a response indicating either success or failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public FollowUserResponse follow(FollowUserRequest request) throws IOException;


    /**
     * @param request contains the data required to fulfill the request.
     * @return a response indicating either success or failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public UnfollowUserResponse unfollow(UnfollowUserRequest request) throws IOException;


    /**
     * @param request A request containing information on the logged in user and another user.
     * @return A response indicating whether the user is followed or not on success, or an error
     * message on failure
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public CheckFollowingResponse checkFollowStatus(CheckFollowingRequest request) throws IOException;

}
