package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.FeedService;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;

public class FeedPresenterTest {

    private StatusRequest request;
    private StatusResponse response;
    private FeedService mockFeedService;
    private FeedPresenter presenter;
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
        mockFeedService = Mockito.mock(FeedService.class);
        Mockito.when(mockFeedService.fetchFeed(request)).thenReturn(response);

        Mockito.when(mockFeedService.fetchFeed(badRequest)).thenReturn(badResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FeedPresenter(new FeedPresenter.View() {}));
        Mockito.when(presenter.getFeedService()).thenReturn(mockFeedService);
    }

    @Test
    public void testFeed_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getFeed(request));
    }

    @Test
    public void testFeed_returnBadResult() throws IOException {
        Assertions.assertEquals(badResponse, presenter.getFeed(badRequest));
    }
}
