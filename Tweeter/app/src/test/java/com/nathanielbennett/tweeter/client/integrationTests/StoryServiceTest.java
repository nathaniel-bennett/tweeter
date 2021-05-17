package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.StoryServiceProxy;
import com.nathanielbennett.tweeter.model.service.StoryService;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class StoryServiceTest {

    private StoryService storyService;
    private StatusRequest validRequest;
    private StatusRequest invalidRequest;
    private final int limit = 5;

    @BeforeEach
    public void setup() {
        storyService = new StoryServiceProxy();
        validRequest = new StatusRequest("dummyUser", limit, null);
        invalidRequest = new StatusRequest("badUser", limit, null);
    }

    @Test
    public void validStoryRequest() throws IOException {
        StatusResponse response = storyService.fetchStory(validRequest);
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(limit, response.getStatuses().size());
    }

    @Test
    public void invalidStoryRequest() throws IOException {
        StatusResponse response = storyService.fetchStory(invalidRequest);
        Assertions.assertNull(response.getStatuses());
    }
}
