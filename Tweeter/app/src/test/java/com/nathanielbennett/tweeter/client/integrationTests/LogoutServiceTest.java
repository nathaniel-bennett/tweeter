package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.LogoutServiceProxy;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
import com.nathanielbennett.tweeter.model.service.LogoutService;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LogoutServiceTest {

    private LogoutRequest goodRequest;
    private LogoutRequest badRequest;
    private LogoutService logoutService;

    @BeforeEach
    public void setup() {
        logoutService = new LogoutServiceProxy();
        goodRequest = new LogoutRequest("dummyUser", new AuthToken("Authorized"));
        badRequest = new LogoutRequest("badUser", null);
    }

    @Test
    public void validRequestTest() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutService.logout(goodRequest);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void invalidRequestTest() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutService.logout(badRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }



}
