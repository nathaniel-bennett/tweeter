package com.nathanielbennett.tweeter.model.service.response;

public class AuthorizationResponse extends TweeterAPIResponse {

    /**
     * Creates a response to a failed request,.
     *
     * @param errorMessage an error message describing why the request was unsuccessful.
     */
    public AuthorizationResponse(String errorMessage) {
        super(errorMessage);
    }

    public AuthorizationResponse() { }
}
