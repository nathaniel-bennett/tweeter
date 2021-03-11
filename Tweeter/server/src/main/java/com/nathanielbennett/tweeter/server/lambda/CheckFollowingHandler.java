package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
import com.nathanielbennett.tweeter.server.service.FollowServiceImpl;

public class CheckFollowingHandler implements RequestHandler<CheckFollowingRequest, CheckFollowingResponse> {
    @Override
    public CheckFollowingResponse handleRequest(CheckFollowingRequest request, Context context) {
        FollowServiceImpl followService = new FollowServiceImpl();
        return followService.checkFollowStatus(request);
    }
}
