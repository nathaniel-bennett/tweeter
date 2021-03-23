package com.nathanielbennett.tweeter.model.service.response;

/**
 * A base class for server responses.
 */
public class TweeterAPIResponse {

    protected boolean success;
    protected String message;

    /**
     * Creates a response to a successful request.
     */
    TweeterAPIResponse() {
        this.success = true;
        this.message = null;
    }

    /**
     * Creates a response to a failed request,.
     *
     * @param message an error message describing why the request was unsuccessful.
     */
    TweeterAPIResponse(String message) {
        this.success = false;
        this.message = message;
    }

    /**
     * Indicates whether the response represents a successful result.
     *
     * @return the success indicator.
     */
    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * The error message for unsuccessful results.
     *
     * @return an error message or null if the response indicates a successful result.
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
