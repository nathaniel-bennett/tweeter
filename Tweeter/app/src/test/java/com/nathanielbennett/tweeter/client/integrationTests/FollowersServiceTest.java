package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.FeedServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.FollowersServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.Service;
import com.nathanielbennett.tweeter.model.service.FeedService;
import com.nathanielbennett.tweeter.model.service.FollowersService;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FollowersServiceTest {

    private FollowRequest goodRequest;
    private FollowRequest badRequest;
    private final int limit = 5;
    private FollowersService followersService;

    @BeforeEach
    public void setup() {
        followersService = new FollowersServiceProxy();
        goodRequest = new FollowRequest("dummyUser", limit, null);
        badRequest = new FollowRequest("badUser", limit,    null);
    }

    @Test
    public void validRequestTest() throws IOException {
        FollowResponse response = followersService.fetchFollowers(goodRequest);
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(limit, response.getRequestedUsers().size());
    }

    @Test
    public void invalidRequestTest() throws IOException {
        FollowResponse response = followersService.fetchFollowers(badRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }



}
