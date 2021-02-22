package com.nathanielbennett.tweeter.model.service.response;

public class LogoutResponse extends Response {
    public LogoutResponse() {
        super(true);
    }

    public LogoutResponse(String failureMessage) {
        super(false, failureMessage);
    }
}
