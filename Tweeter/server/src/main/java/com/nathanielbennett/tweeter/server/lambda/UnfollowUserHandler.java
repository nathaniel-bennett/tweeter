package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
import com.nathanielbennett.tweeter.server.service.FollowServiceImpl;

public class UnfollowUserHandler implements RequestHandler<UnfollowUserRequest, UnfollowUserResponse> {
    @Override
    public UnfollowUserResponse handleRequest(UnfollowUserRequest request, Context context) {
        FollowServiceImpl followService = new FollowServiceImpl();
        return followService.unfollow(request);


    }
}
