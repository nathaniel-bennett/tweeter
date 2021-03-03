package com.nathanielbennett.tweeter.client.presenter;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.model.service.FollowingServiceProxy;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class FollowingPresenterTest {

    private FollowRequest request;
    private FollowResponse response;
    private FollowingServiceProxy mockFollowingService;
    private FollowingPresenter presenter;
    private FollowRequest badRequest;
    private FollowResponse badResponse;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new FollowRequest(currentUser.getAlias(), 3, null);
        response = new FollowResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        badRequest = new FollowRequest(resultUser1.getAlias(), -1, "bernard");
        badResponse = new FollowResponse(null, true);

        // Create a mock FollowingService
        mockFollowingService = Mockito.mock(FollowingServiceProxy.class);
        Mockito.when(mockFollowingService.getFollowees(request)).thenReturn(response);

        Mockito.when(mockFollowingService.getFollowees(badRequest)).thenReturn(badResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FollowingPresenter(new FollowingPresenter.View() {}));
        Mockito.when(presenter.getFollowingService()).thenReturn(mockFollowingService);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getFollowing(request));
    }

    @Test
    public void testGetFollowing_returnBadResult() throws IOException {
        Assertions.assertEquals(badResponse, presenter.getFollowing(badRequest));
    }
}
