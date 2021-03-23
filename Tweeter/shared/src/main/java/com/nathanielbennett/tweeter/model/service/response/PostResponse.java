package com.nathanielbennett.tweeter.model.service.response;

public class PostResponse extends TweeterAPIResponse {

    /**
     * Creates a new PostResponse indicating that the attempted Post failed, with
     * an error message explaining why failure occurred.
     *
     * @param message An error message indicating wy the PostRequest failed.
     */
    public PostResponse(String message) {
        super(message);
    }

    /**
     * Creates a new PostResponse indicating that the attempted Post was successful.
     */
    public PostResponse() { }
}
