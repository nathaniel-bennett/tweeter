package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.RegisterService;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.WeakPasswordException;


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

        if (userDAO.createUser(null)) {
            User user = userDAO.getUser(request.getUsername());
            AuthTokenDAO authTokenDAO = new AuthTokenDAO();
            AuthToken authToken = authTokenDAO.createToken(request.getUsername());
            return new RegisterResponse(user, authToken);
        }

        return new RegisterResponse("Unable to create user");
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
}
