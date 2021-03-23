package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.FeedServiceProxy;
import com.nathanielbennett.tweeter.model.service.FeedService;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FeedServiceTest {

    private StatusRequest goodRequest;
    private StatusRequest badRequest;
    private final int limit = 5;
    private FeedService feedService;

    @BeforeEach
    public void setup() {
        feedService = new FeedServiceProxy();
        goodRequest = new StatusRequest("dummyUser", limit, "");
        badRequest = new StatusRequest("badUser", limit, "");
    }

    @Test
    public void validRequestTest() throws IOException {
        StatusResponse response = feedService.fetchFeed(goodRequest);
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(limit, response.getStatuses().size());
    }

    @Test
    public void invalidRequestTest() throws IOException {
        StatusResponse response = feedService.fetchFeed(badRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }



}
