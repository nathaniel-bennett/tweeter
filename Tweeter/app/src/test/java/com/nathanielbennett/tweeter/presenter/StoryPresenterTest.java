package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.StoryService;
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
    private StoryService mockStoryService;
    private StoryPresenter presenter;
    private StatusRequest badRequest;
    private StatusResponse badResponse;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new StatusRequest(currentUser, 3, "I like to play Mario Bros.");
        response = new StatusResponse(false, new ArrayList<>());

        badRequest = new StatusRequest(resultUser1, -1, null);
        badResponse = new StatusResponse(true, null);

        // Create a mock FollowingService
        mockStoryService = Mockito.mock(StoryService.class);
        Mockito.when(mockStoryService.fetchStory(request)).thenReturn(response);

        Mockito.when(mockStoryService.fetchStory(badRequest)).thenReturn(badResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryService);
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
