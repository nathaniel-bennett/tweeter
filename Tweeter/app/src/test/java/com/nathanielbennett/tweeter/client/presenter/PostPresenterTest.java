package com.nathanielbennett.tweeter.client.presenter;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.model.service.PostServiceProxy;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class PostPresenterTest {

    private PostRequest request;
    private PostResponse response;
    private PostServiceProxy mockPostServiceProxy;
    private PostPresenter presenter;
    private PostRequest badRequest;
    private PostResponse badResponse;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new PostRequest("This is a status", "FirstNameLastName", new AuthToken());
        response = new PostResponse();

        badRequest = new PostRequest(null, null, null);
        badResponse = new PostResponse("error");

        // Create a mock FollowingService
        mockPostServiceProxy = Mockito.mock(PostServiceProxy.class);
        Mockito.when(mockPostServiceProxy.addPost(request)).thenReturn(response);

        Mockito.when(mockPostServiceProxy.addPost(badRequest)).thenReturn(badResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new PostPresenter(new PostPresenter.View() {}));
        Mockito.when(presenter.getPostService()).thenReturn(mockPostServiceProxy);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.post(request));
    }

    @Test
    public void testGetFollowing_returnBadResult() throws IOException {
        Assertions.assertEquals(badResponse, presenter.post(badRequest));
    }
}
