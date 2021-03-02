package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public class UnfollowUserRequest extends AuthorizedRequest {
    private final String userToUnfollow;

    /**
     * Creates a new Request to follow a user.
     * @param username The username of the user making the Unfollow request.
     * @param authToken The auth token associated with the logged in user.
     * @param userToUnfollow The username of the user to stop following.
     */
    public UnfollowUserRequest(String username, AuthToken authToken, String userToUnfollow) {
        super(username, authToken);
        this.userToUnfollow = userToUnfollow;
    }

    public String getUserToUnfollow() {
        return userToUnfollow;
    }
}
