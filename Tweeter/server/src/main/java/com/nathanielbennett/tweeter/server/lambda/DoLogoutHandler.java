package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.server.service.LogoutServiceImpl;

public class DoLogoutHandler implements RequestHandler<LogoutRequest, LogoutResponse> {

    @Override
    public LogoutResponse handleRequest(LogoutRequest logoutRequest, Context context) {
        LogoutServiceImpl logoutService = new LogoutServiceImpl();
        return logoutService.logout(logoutRequest);
    }
}
