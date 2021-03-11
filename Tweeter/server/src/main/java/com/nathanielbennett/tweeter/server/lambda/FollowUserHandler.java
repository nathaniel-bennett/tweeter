package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.server.service.FollowServiceImpl;

public class FollowUserHandler implements RequestHandler<FollowUserRequest, FollowUserResponse> {
    @Override
    public FollowUserResponse handleRequest(FollowUserRequest request, Context context) {
        FollowServiceImpl followService = new FollowServiceImpl();
        return followService.follow(request);
    }
}
