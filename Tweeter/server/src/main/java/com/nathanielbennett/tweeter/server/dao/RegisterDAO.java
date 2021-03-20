package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.server.DataCache;
import com.nathanielbennett.tweeter.server.exceptions.HandleTakenException;
import com.nathanielbennett.tweeter.server.exceptions.WeakPasswordException;

public class RegisterDAO {
    private static final AuthToken authToken1 = new AuthToken();
    private static final DataCache dc = DataCache.getInstance();

    /**
     * Performs a user registration and if successful, returns the registered user and an auth
     * token. The current implementation is hard-coded to return a dummy user and doesn't actually
     * make a network request.
     * @param request contains all information needed to register a user.
     * @return the register response.
     */
    public RegisterResponse register(RegisterRequest request) {
        if (dc.getUser(request.getUsername()) != null){
            throw new HandleTakenException("Username taken; please try another username");

        } else {
            if (request.getPassword().length() < 8) {
                throw new WeakPasswordException("Password is not long enough (must be 8 characters or more)");
            }

            User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(),
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            user.setImageBytes(request.getImage());
            dc.registerUser(user);
            return new RegisterResponse(user, authToken1);
        }
    }
}
