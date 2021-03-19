package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.LogoutService;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.server.dao.LogoutDAO;

public class LogoutServiceImpl implements LogoutService {
    @Override
    public LogoutResponse logout(LogoutRequest request) {

        return null;
    }

    /**
     * Returns an instance of {@link LogoutDAO}. Allows mocking of the LogoutDAO class
     * for testing purposes. All usages of LogoutDAO should get their LogoutDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public LogoutDAO getLogoutDao() {
        return new LogoutDAO();
    }
}
