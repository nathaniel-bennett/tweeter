package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public class UnfollowUserRequest implements TweeterAPIRequest {
    private final String username;
    private final AuthToken authToken;
    private final String userToUnfollow;


    /**
     * Creates a new Request to follow a user.
     * @param username The username of the user making the Unfollow request.
     * @param authToken The auth token associated with the logged in user.
     * @param userToUnfollow The username of the user to stop following.
     */
    public UnfollowUserRequest(String username, AuthToken authToken, String userToUnfollow) {
        this.username = username;
        this.authToken = authToken;
        this.userToUnfollow = userToUnfollow;
    }

    public String getUsername() {
        return username;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public String getUserToUnfollow() {
        return userToUnfollow;
    }
}
