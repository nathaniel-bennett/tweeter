package com.nathanielbennett.tweeter.model.service.response;

public class LogoutResponse extends Response {

    /**
     * Creates a response indicating that the user was successfully logged out.
     */
    public LogoutResponse() {
        super();
    }

    /**
     * Creates a response indicating that the logout attempt was unsuccessful.
     *
     * @param message an error message describing why the logout was unsuccessful.
     */
    public LogoutResponse(String message) {
        super(message);
    }
}
