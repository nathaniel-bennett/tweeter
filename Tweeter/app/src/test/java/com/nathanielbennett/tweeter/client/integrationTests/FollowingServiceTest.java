package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.FeedServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.FollowersServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.FollowingServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.Service;
import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
import com.nathanielbennett.tweeter.model.service.FeedService;
import com.nathanielbennett.tweeter.model.service.FollowersService;
import com.nathanielbennett.tweeter.model.service.FollowingService;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FollowingServiceTest {

    private FollowRequest goodRequest;
    private FollowRequest badRequest;
    private final int limit = 5;
    private FollowingService followingService;

    @BeforeEach
    public void setup() {
        followingService = new FollowingServiceProxy();
        goodRequest = new FollowRequest("dummyUser", limit, null);
        badRequest = new FollowRequest("badUser", limit,    null);
    }

    @Test
    public void validRequestTest() throws IOException, TweeterRemoteException {
        FollowResponse response = followingService.getFollowees(goodRequest);
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(limit, response.getRequestedUsers().size());
    }

    @Test
    public void invalidRequestTest() throws IOException, TweeterRemoteException {
        FollowResponse response = followingService.getFollowees(badRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }



}
