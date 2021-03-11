package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.service.FeedServiceImpl;
import com.nathanielbennett.tweeter.server.service.FollowingServiceImpl;

public class GetFeedHandler implements RequestHandler<StatusRequest, StatusResponse> {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        FeedServiceImpl feedService = new FeedServiceImpl();
        return feedService.fetchFeed(request);
    }
}
