package com.nathanielbennett.tweeter.model.service.response;

import com.nathanielbennett.tweeter.model.domain.Status;

import java.util.List;

public class StatusResponse extends PagedResponse {
    List<Status> statuses;

    /**
     * creates a response indicating that the request was successful.
     * @param success status of request.
     * @param hasMorePages indicator of if more pages are available.
     * @param statuses the list of statues for the requested user.
     */
    public StatusResponse(boolean success, boolean hasMorePages, List<Status> statuses) {
        super(success, hasMorePages);
        this.statuses = statuses;
    }

    /**
     * Creates a response indicating the request was unsuccessful.
     * @param success status of request.
     * @param message message indicating failure.
     * @param hasMorePages indicator of if more pages are available.
     */
    public StatusResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message, hasMorePages);
    }

    /**
     * Getter for list of statuses.
     * @return statuses
     */
    public List<Status> getStatuses() {
        return statuses;
    }
}
