package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.server.service.FollowingServiceImpl;

/**
 * An AWS lambda function that returns the users a user is following.
 */
public class GetFollowingHandler implements RequestHandler<FollowRequest, FollowResponse> {

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
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        LambdaLogger logger = context.getLogger();
        FollowingServiceImpl service = new FollowingServiceImpl(logger);

        logger.log("Received request to get followers; alias: " + request.getFollowAlias() + "; lastFollowAlias: " + request.getLastFollowAlias());

        FollowResponse response = service.getFollowees(request);

        if (response.getSuccess() && response.getRequestedUsers() != null && response.getRequestedUsers().size() > 0) {
            logger.log("Response formed; first user: " + response.getRequestedUsers().get(0) + "; last user: " + response.getRequestedUsers().get(response.getRequestedUsers().size()-1));
        } else {
            logger.log("Response formed, but didn't contain users");
        }

        return response;
    }
}
