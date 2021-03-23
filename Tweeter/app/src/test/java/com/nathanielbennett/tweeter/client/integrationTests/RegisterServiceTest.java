package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.PostServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.RegisterServiceProxy;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
import com.nathanielbennett.tweeter.model.service.PostService;
import com.nathanielbennett.tweeter.model.service.RegisterService;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RegisterServiceTest {

    private RegisterRequest goodRequest;
    private RegisterRequest badRequest;
    private RegisterService registerService;

    @BeforeEach
    public void setup() {
        registerService = new RegisterServiceProxy();
        goodRequest = new RegisterRequest("Bartholomew", "Desperation", "Mr.Desperate", "GOODPASSWORD", new byte[1]);
        badRequest = new RegisterRequest(null, null, null, null, null);
    }

    @Test
    public void validRequestTest() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerService.register(goodRequest);
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertNull(response.getErrorMessage());
        Assertions.assertNotNull(response.getAuthToken());
        Assertions.assertNotNull(response.getUser());
    }

    @Test
    public void invalidRequestTest() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerService.register(badRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }



}
