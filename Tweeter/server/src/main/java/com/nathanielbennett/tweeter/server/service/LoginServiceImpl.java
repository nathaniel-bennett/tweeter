package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {
        if (request == null) {
            throw new BadRequestException("Message body missing or malformed");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Username missing from login request");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new BadRequestException("Password missing from login request");
        }

        UserDAO userDAO = getUserDAO();

        // TODO: change to StoredUser object that has hashed password...
        User user = userDAO.getUser(request.getUsername());
        if (user == null) {
            return new LoginResponse("Username not registered.");
        }

        AuthTokenDAO authTokenDAO = getAuthTokenDAO();
        AuthToken authToken = authTokenDAO.createToken(request.getUsername());

        // TODO: login stuff!

        return new LoginResponse(user, authToken);
    }

    /**
     * Returns an instance of {@link LoginDAO}. Allows mocking of the LoginDAO class
     * for testing purposes. All usages of LoginDAO should get their LoginDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }
}
