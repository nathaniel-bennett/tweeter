package com.nathanielbennett.tweeter.model.service.response;


public class UnfollowUserResponse extends TweeterAPIResponse {

    /**
     * Creates a response indicating that the corresponding UnfollowUser request was unsuccessful.
     *
     * @param message an error message describing why the UnfollowUser request was unsuccessful.
     */
    public UnfollowUserResponse(String message) {
        super(message);
    }

    /**
     * Creates a response indicating that the corresponding UnfollowUser request was successful.
     *
     */
    public UnfollowUserResponse() { }
}
