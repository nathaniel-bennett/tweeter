package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.service.StoryServiceImpl;

public class GetStoryHandler implements RequestHandler<StatusRequest, StatusResponse> {

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
        StoryServiceImpl storyService = new StoryServiceImpl();

        LambdaLogger logger = context.getLogger();

        logger.log("Received GetStory Request; alias: " + request.getAlias() + "; limit: " + request.getLimit() + "; lastTimestamp: " + request.getLastTimestamp());

        StatusResponse response = storyService.fetchStory(request);

        if (response.getSuccess() && response.getStatuses() != null && response.getStatuses().size() > 0) {
            Status firstStatus = response.getStatuses().get(0);
            Status lastStatus = response.getStatuses().get(response.getStatuses().size() - 1);

            logger.log("Response retrieved; first: " + firstStatus.getStatusMessage() + " (date: " + firstStatus.getDatePosted() + "); last: " + lastStatus.getStatusMessage() + " (date: " + lastStatus.getDatePosted() + ")");
        } else {
            logger.log("Response didn't have statuses");
        }

        return response;
    }
}
