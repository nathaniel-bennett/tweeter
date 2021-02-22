package com.nathanielbennett.tweeter.model.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    private String authTokenID;

    public AuthToken() {
        authTokenID = UUID.randomUUID().toString();
    }

    public String getTokenID() {
        return authTokenID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;

        return this.authTokenID.equals(authToken.authTokenID);
    }

    @Override
    public int hashCode() {
        return authTokenID.hashCode();
    }
}
