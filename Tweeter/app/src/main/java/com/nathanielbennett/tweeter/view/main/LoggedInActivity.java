package com.nathanielbennett.tweeter.view.main;

import androidx.appcompat.app.AppCompatActivity;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;

public abstract class LoggedInActivity extends AppCompatActivity {
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String LOGGED_IN_USER_KEY = "LoggedInUser";

    protected AuthToken authToken;
    protected User loggedInUser;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
