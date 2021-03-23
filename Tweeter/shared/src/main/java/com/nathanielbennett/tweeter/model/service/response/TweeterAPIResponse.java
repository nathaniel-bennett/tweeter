package com.nathanielbennett.tweeter.model.service.response;

/**
 * A base class for server responses.
 */
public class TweeterAPIResponse {

    protected boolean success;
    protected String errorMessage;

    /**
     * Creates a response to a successful request.
     */
    TweeterAPIResponse() {
        this.success = true;
        this.errorMessage = null;
    }

    /**
     * Creates a response to a failed request,.
     *
     * @param errorMessage an error message describing why the request was unsuccessful.
     */
    TweeterAPIResponse(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
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
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
