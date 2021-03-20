package com.nathanielbennett.tweeter.model.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    private String authTokenID;

    public AuthToken() {
        authTokenID = UUID.randomUUID().toString();
    }

    public String getAuthTokenID() {
        return authTokenID;
    }

    @Override
    public String toString() {
        return authTokenID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;

        return this.authTokenID.equals(authToken.authTokenID);
    }

    public void setAuthTokenID(String authTokenID) {
        this.authTokenID = authTokenID;
    }

    @Override
    public int hashCode() {
        return authTokenID.hashCode();
    }
}
