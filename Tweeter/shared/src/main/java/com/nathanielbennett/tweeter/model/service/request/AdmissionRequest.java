package com.nathanielbennett.tweeter.model.service.request;

public abstract class AdmissionRequest implements TweeterAPIRequest {

    public AdmissionRequest() {
    }

    private String username;

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

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

    public void setUsername(String username){
        this.username = username;
    }
}
