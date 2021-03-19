package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.server.dao.LoginDAO;
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

        return getLoginDao().login(request);
    }

    /**
     * Returns an instance of {@link LoginDAO}. Allows mocking of the LoginDAO class
     * for testing purposes. All usages of LoginDAO should get their LoginDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public LoginDAO getLoginDao() {
        return new LoginDAO();
    }
}
