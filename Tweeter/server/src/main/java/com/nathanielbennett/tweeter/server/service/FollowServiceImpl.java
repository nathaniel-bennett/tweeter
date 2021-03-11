package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

import java.io.IOException;

public class FollowServiceImpl implements FollowService {

    @Override
    public FollowUserResponse follow(FollowUserRequest request) throws IOException {
        return null;
    }

    @Override
    public UnfollowUserResponse unfollow(UnfollowUserRequest request) throws IOException {
        return null;
    }

    @Override
    public CheckFollowingResponse checkFollowStatus(CheckFollowingRequest request) throws IOException {
        return null;
    }
}
