package com.nathanielbennett.tweeter.client.presenter;

import com.nathanielbennett.tweeter.client.model.service.StoryServiceProxy;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;

public class StoryPresenterTest {

    private StatusRequest request;
    private StatusResponse response;
    private StoryServiceProxy mockStoryServiceProxy;
    private StoryPresenter presenter;
    private StatusRequest badRequest;
    private StatusResponse badResponse;

    @BeforeEach
    public void setup() throws IOException {

        request = new StatusRequest("@ME", 3, "I like to play Mario Bros.");
        response = new StatusResponse(false, new ArrayList<>());

        badRequest = new StatusRequest("@ME", -1, null);
        badResponse = new StatusResponse(true, null);

        // Create a mock FollowingService
        mockStoryServiceProxy = Mockito.mock(StoryServiceProxy.class);
        Mockito.when(mockStoryServiceProxy.fetchStory(request)).thenReturn(response);

        Mockito.when(mockStoryServiceProxy.fetchStory(badRequest)).thenReturn(badResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryServiceProxy);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getStory(request));
    }

    @Test
    public void testGetFollowing_returnBadResult() throws IOException {
        Assertions.assertEquals(badResponse, presenter.getStory(badRequest));
    }
}
