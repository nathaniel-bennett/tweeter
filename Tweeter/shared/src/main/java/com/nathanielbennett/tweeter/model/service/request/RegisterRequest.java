package com.nathanielbennett.tweeter.model.service.request;

public class RegisterRequest extends AdmissionRequest {

    private final String firstName;

    private final String lastName;

    private final byte[] image;


    public RegisterRequest(String firstName, String lastName, String username, String password, byte[] image) {
        super(username, password);

        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public byte[] getImage() {
        return image;
    }
}
