package com.nathanielbennett.tweeter.model.service.response;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

import androidx.annotation.Nullable;

import com.nathanielbennett.tweeter.model.domain.User;

import java.util.List;
import java.util.Objects;

public class FollowersResponse extends PagedResponse {

    private List<User> followers;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowersResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param followers the followers to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public FollowersResponse(List<User> followers, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followers = followers;
    }

    /**
     * Returns the followers for the corresponding request.
     *
     * @return the followers.
     */
    public List<User> getFollowers() {
        return followers;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        FollowersResponse that = (FollowersResponse) obj;

        return (Objects.equals(followers, that.followers) &&
                Objects.equals(this.getMessage(), that.getMessage())
                && this.isSuccess() == that.isSuccess());

    }

    @Override
    public int hashCode() {
        return Objects.hash(followers);
    }
}
