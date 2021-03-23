package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.DataCache;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class StoryDAO {
    private static final DataCache dc = DataCache.getInstance();

    /**
     * Returns the status that the user specified in the request. Uses information in the request object
     * to limit the number of statuses returned and to return the next set of statuses after any
     * that were returned in the previous request. The current implementation returns generated data
     * and doesn't actually make a network request.
     *
     * @param request contains information about the user whose status are to be returned
     * @return the story response.
     */
    public StatusResponse getStory(StatusRequest request) {
        User user = dc.getUser(request.getAlias());
        if (user == null) {
            throw new BadRequestException("The requested user was not found in database.");
        }


        List<Status> allStatuses = getStoryFromDC(user);
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());



        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int storyIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);

            for (int limitCounter = 0; storyIndex < allStatuses.size() && limitCounter < request.getLimit(); storyIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(storyIndex));
            }

            hasMorePages = storyIndex < allStatuses.size();
        }

        return new StatusResponse(hasMorePages, responseStatuses);
    }

    /**
     * Determines the index for the first status in the specified 'allStatuses' list that should be
     * returned in the current request. This will be the index of the next status after the specified
     * 'message'.
     *
     * @param message the last message seen.
     * @param allStatuses all of the statuses.
     * @return the index of the first status to be returned.
     */
    private int getStatusStartingIndex(String message, List<Status> allStatuses) {
        int statusIndex = 0;

        if (message != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if (message.equals(allStatuses.get(i).getStatusMessage())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return;
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }
    
    /**
     * Returns the list of dummy status for the story. This is written as a separate method to allow
     * mocking of the story.
     *
     * @return the dummy story.
     */
    List<Status> getStoryFromDC(User user) {
        return dc.getStatuses(user);
    }
}
