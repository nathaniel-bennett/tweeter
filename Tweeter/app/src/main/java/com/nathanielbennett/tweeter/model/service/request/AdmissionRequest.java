package com.nathanielbennett.tweeter.model.service.request;

public abstract class AdmissionRequest {
    private String username;
    private String password; // TODO: change to hash? Or handle hashing in Service class?

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
