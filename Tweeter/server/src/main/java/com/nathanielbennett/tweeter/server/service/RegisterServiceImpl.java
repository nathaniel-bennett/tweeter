package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.RegisterService;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.HandleTakenException;
import com.nathanielbennett.tweeter.server.exceptions.WeakPasswordException;
import com.nathanielbennett.tweeter.server.dao.LogoutDAO;
import com.nathanielbennett.tweeter.server.dao.RegisterDAO;
import com.nathanielbennett.tweeter.server.model.StoredUser;

public class RegisterServiceImpl implements RegisterService {

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (request == null) {
            throw new BadRequestException("Message body missing or malformed");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Registration request missing username");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new WeakPasswordException("You must create a password to register");
        }

        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new BadRequestException("First name missing from registration request");
        }

        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new BadRequestException("Last name missing from registration request");
        }

        if (request.getImage() == null || request.getImage().length == 0) {
            throw new BadRequestException("A profile picture is required in order to register");
        }

        UserDAO userDAO = getUserDAO();

        StoredUser existingUser = userDAO.getUser(request.getUsername());
        if (existingUser != null) {
            throw new HandleTakenException("Requested user handle is taken; please try another.");
        }

        String hashedPassword = request.getPassword(); // TODO: add hashing

        String imageLocation = "lol"; // TODO: create S3 image resource from bytes, fetch resource location here


        StoredUser storedUser = new StoredUser(request.getFirstName(), request.getLastName(), request.getPassword(), request.getUsername(), imageLocation, 0, 0);

        userDAO.createUser(storedUser);

        User user = storedUser.toUser();
        AuthTokenDAO authTokenDAO = getAuthTokenDAO();
        AuthToken authToken = authTokenDAO.createToken(request.getUsername());

        return new RegisterResponse(user, authToken);
    }

    /**
     * Returns an instance of {@link LogoutDAO}. Allows mocking of the RegisterDAO class
     * for testing purposes. All usages of RegisterDAO should get their RegisterDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
