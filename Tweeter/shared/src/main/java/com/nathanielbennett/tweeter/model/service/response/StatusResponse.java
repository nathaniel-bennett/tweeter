package com.nathanielbennett.tweeter.model.service.response;

import com.nathanielbennett.tweeter.model.domain.Status;

import java.util.List;

public class StatusResponse extends PagedResponse {
    List<Status> statuses;

    /**
     * creates a response indicating that the request was successful.
     * @param hasMorePages indicator of if more pages are available.
     * @param statuses the list of statues for the requested user.
     */
    public StatusResponse(boolean hasMorePages, List<Status> statuses) {
        super(hasMorePages);
        this.statuses = statuses;
    }

    /**
     * Creates a response indicating the request was unsuccessful.
     * @param message message indicating why the request was unsuccessful.
     */
    public StatusResponse(String message) {
        super(message);
    }

    public StatusResponse() { }

    /**
     * Getter for list of statuses.
     * @return statuses
     */
    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
