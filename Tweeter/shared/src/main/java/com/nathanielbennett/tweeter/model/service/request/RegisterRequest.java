package com.nathanielbennett.tweeter.model.service.request;

public class RegisterRequest extends AdmissionRequest {

    private String firstName;
    private String lastName;
    private byte[] image;

    public RegisterRequest(String firstName, String lastName, String username, String password, byte[] image) {
        super(username, password);

        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    public RegisterRequest() { }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
