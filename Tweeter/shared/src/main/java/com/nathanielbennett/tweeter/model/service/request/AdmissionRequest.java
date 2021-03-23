package com.nathanielbennett.tweeter.model.service.request;

public abstract class AdmissionRequest implements TweeterAPIRequest {

    private String username;
    private String password;

    public AdmissionRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AdmissionRequest() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
