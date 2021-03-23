package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.PostServiceProxy;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
import com.nathanielbennett.tweeter.model.service.PostService;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
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
    public void setup() {
        postService = new PostServiceProxy();
        goodRequest = new PostRequest("This is a status", "dummyUser", new AuthToken("Authorized"));
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
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }



}
