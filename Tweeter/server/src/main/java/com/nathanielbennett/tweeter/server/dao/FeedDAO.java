package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.DataCache;

import java.util.ArrayList;
import java.util.List;

public class FeedDAO {
    private static final DataCache dc = DataCache.getInstance();

    public StatusResponse getFeed(StatusRequest request) {
        List<Status> allStatuses = getFeedFromDC(request.getUserToGet());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int storyIndex = getStatusStartingIndex(request.getLastStatusSent(), allStatuses);

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
                if(message.equals(allStatuses.get(i).getStatusMessage())) {
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
     * Returns the list of dummy status for the feed. This is written as a separate method to allow
     * mocking of the feed.
     *
     * @return the dummy feed.
     */
    List<Status> getFeedFromDC(User user) {
        List<Status> story = new ArrayList<>();
        List<User> following = dc.getFollowing(user);

        for (User amFollowing : following) {
            story.addAll(dc.getStatuses(amFollowing));
        }
        return story;
    }
}
