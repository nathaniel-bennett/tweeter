package com.nathanielbennett.tweeter.model.service.response;

import android.os.Build;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;

import java.util.List;
import java.util.Objects;

/**
 * A paged response for a {@link FollowRequest}.
 */
public class FollowResponse extends PagedResponse {

    private List<User> requestedUsers;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param requestedUsers the the users to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public FollowResponse(List<User> requestedUsers, boolean hasMorePages) {
        super(true, hasMorePages);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return (Objects.equals(requestedUsers, that.requestedUsers) &&
                    Objects.equals(this.getMessage(), that.getMessage()) &&
                    this.isSuccess() == that.isSuccess());
        }

        return true;
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(requestedUsers);
        }

        return (int)(requestedUsers.size() / Math.PI);
    }
}
