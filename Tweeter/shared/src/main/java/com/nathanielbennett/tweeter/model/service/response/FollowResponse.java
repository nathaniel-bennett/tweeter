package com.nathanielbennett.tweeter.model.service.response;


import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;

import java.util.List;

/**
 * A paged response for a {@link FollowRequest}.
 */
public class FollowResponse extends PagedResponse {

    private final List<User> requestedUsers;

    /**
     * Creates a response indicating that the corresponding Follow request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message an error message describing why the Follow request was unsuccessful.
     */
    public FollowResponse(String message) {
        super(message);
        requestedUsers = null;
    }

    /**
     * Creates a response indicating that the corresponding Follow request was successful.
     *
     * @param requestedUsers the the users to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public FollowResponse(List<User> requestedUsers, boolean hasMorePages) {
        super(hasMorePages);
        this.requestedUsers = requestedUsers;
    }

    /**
     * Returns the followees for the corresponding request.
     *
     * @return the followees.
     */
    public List<User> getRequestedUsers() {
        return requestedUsers;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowResponse that = (FollowResponse) param;

        if (requestedUsers == null) {
            return super.equals(param);
        } else {
            return this.requestedUsers.equals(that.requestedUsers);
        }
    }

    @Override
    public int hashCode() {
         if (requestedUsers != null) {
             return requestedUsers.hashCode();
         } else {
             return super.hashCode();
         }
    }


}
