package com.nathanielbennett.tweeter.model.service.request;

public abstract class AdmissionRequest implements TweeterAPIRequest {
    private final String username;
    private final String password;

    public AdmissionRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
