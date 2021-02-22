package com.nathanielbennett.tweeter.model.service.response;

/**
 * A base class for server responses.
 */
public class Response {

    private final boolean success;
    private final String message;

    /**
     * Creates a response to a successful request.
     */
    Response() {
        this.success = true;
        this.message = null;
    }

    /**
     * Creates a response to a failed request,.
     *
     * @param message an error message describing why the request was unsuccessful.
     */
    Response(String message) {
        this.success = false;
        this.message = message;
    }

    /**
     * Indicates whether the response represents a successful result.
     *
     * @return the success indicator.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * The error message for unsuccessful results.
     *
     * @return an error message or null if the response indicates a successful result.
     */
    public String getMessage() {
        return message;
    }
}
