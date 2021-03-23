package com.nathanielbennett.tweeter.model.service.response;


public class FollowUserResponse extends TweeterAPIResponse {

    /**
     * Creates a response indicating that the corresponding FollowUser request was unsuccessful.
     *
     * @param message an error message describing why the FollowUser request was unsuccessful.
     */
    public FollowUserResponse(String message) {
        super(message);
    }

    /**
     * Creates a response indicating that the corresponding FollowUser request was successful.
     *
     */
    public FollowUserResponse() { }

}
