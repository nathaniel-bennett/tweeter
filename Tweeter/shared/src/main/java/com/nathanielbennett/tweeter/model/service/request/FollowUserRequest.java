package com.nathanielbennett.tweeter.model.service.request;

import com.nathanielbennett.tweeter.model.domain.AuthToken;

public class FollowUserRequest extends AuthorizedRequest {

    public FollowUserRequest() {
    }

    public void setUserToFollow(String userToFollow) {
        this.userToFollow = userToFollow;
    }

    private String userToFollow;


    /**
     * Creates a new Request to follow a user.
     * @param username The username of the user making the Follow request.
     * @param authToken The auth token associated with the logged in user.
     * @param userToFollow The username of the user to begin following.
     */
    public FollowUserRequest(String username, AuthToken authToken, String userToFollow) {
        super(username, authToken);
        this.userToFollow = userToFollow;
    }

    public String getUserToFollow() {
        return userToFollow;
    }
}
