package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.FeedServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.FollowersServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.LoginServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.Service;
import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
import com.nathanielbennett.tweeter.model.service.FeedService;
import com.nathanielbennett.tweeter.model.service.FollowersService;
import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoginServiceTest {

    private LoginRequest goodRequest;
    private LoginRequest badRequest;
    private LoginService loginService;

    @BeforeEach
    public void setup() {
        loginService = new LoginServiceProxy();
        goodRequest = new LoginRequest("dummyUser", "dummyUser");
        badRequest = new LoginRequest("badUser", null);
    }

    @Test
    public void validRequestTest() throws IOException, TweeterRemoteException {
        LoginResponse response = loginService.login(goodRequest);
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertNotNull(response.getAuthToken());
        Assertions.assertNotNull(response.getUser());
    }

    @Test
    public void invalidRequestTest() throws IOException, TweeterRemoteException {
        LoginResponse response = loginService.login(badRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }



}
