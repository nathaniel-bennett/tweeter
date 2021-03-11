package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.FollowService;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

public class FollowServiceImpl implements FollowService {

    @Override
    public FollowUserResponse follow(FollowUserRequest request) {
        return null;
    }

    @Override
    public UnfollowUserResponse unfollow(UnfollowUserRequest request) {
        return null;
    }

    @Override
    public CheckFollowingResponse checkFollowStatus(CheckFollowingRequest request) {
        return null;
    }
}
