package com.nathanielbennett.tweeter.model.service.request;

public class RegisterRequest implements Request {

    private String firstName;

    private String lastName;

    private String username;

    private String password; // TODO: should be a hash??

    private byte[] image;


    public RegisterRequest(String firstName, String lastName, String username, String password) {
        // TODO: add image to this constructor

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getImage() {
        return image;
    }
}