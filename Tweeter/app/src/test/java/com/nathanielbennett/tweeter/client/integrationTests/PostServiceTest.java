package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.LoginServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.PostServiceProxy;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.PostService;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PostServiceTest {

    private PostRequest goodRequest;
    private PostRequest badRequest;
    private PostService postService;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException
    {
        LoginService service = new LoginServiceProxy();
        LoginResponse response = service.login(new LoginRequest("testUser", "testUser"));
        AuthToken token = response.getAuthToken();
        postService = new PostServiceProxy();
        goodRequest = new PostRequest("This is a status", "testUser", token);
        badRequest = new PostRequest("This is a bad status", "badUser", new AuthToken());
    }

    @Test
    public void validRequestTest() throws IOException, TweeterRemoteException {
        PostResponse response = postService.addPost(goodRequest);
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertNull(response.getErrorMessage());
    }

    @Test
    public void invalidRequestTest() throws IOException, TweeterRemoteException {
        PostResponse response = postService.addPost(badRequest);
        Assertions.assertNotNull(response.getErrorMessage());
    }



}
