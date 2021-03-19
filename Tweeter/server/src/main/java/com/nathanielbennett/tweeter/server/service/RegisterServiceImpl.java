package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.RegisterService;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.server.dao.LogoutDAO;
import com.nathanielbennett.tweeter.server.dao.RegisterDAO;

public class RegisterServiceImpl implements RegisterService {

    @Override
    public RegisterResponse register(RegisterRequest request) {

        return null;
    }

    /**
     * Returns an instance of {@link LogoutDAO}. Allows mocking of the RegisterDAO class
     * for testing purposes. All usages of RegisterDAO should get their RegisterDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public RegisterDAO getRegisterDAO() {
        return new RegisterDAO();
    }
}
