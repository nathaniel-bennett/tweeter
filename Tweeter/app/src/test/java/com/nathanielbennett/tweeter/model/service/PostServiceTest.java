package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class PostServiceTest {

    private PostRequest validRequest;
    private PostRequest invalidRequest;
    private PostRequest otherInvalidRequest;

    private PostResponse successResponse;
    private PostResponse failureResponse;

    private PostService postServiceSpy;


    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup request objects to use in the tests
        validRequest = new PostRequest("THis is a status", "FirstNameLastName", new AuthToken());
        invalidRequest = new PostRequest(null, null, null);
        otherInvalidRequest = new PostRequest("abcdefg", "philberta", new AuthToken());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.addToStory(validRequest)).thenReturn(successResponse);

        failureResponse = new PostResponse("No users found");
        Mockito.when(mockServerFacade.addToStory(otherInvalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        postServiceSpy = Mockito.spy(new PostService());
        Mockito.when(postServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowingService#fetchFollowing(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */

    @Test
    public void testAddPost_validRequest_correctResponse() throws IOException {
        PostResponse response = postServiceSpy.addPost(validRequest);
        Assertions.assertEquals(successResponse, response);
    }




    /**
     * Verify that for failed requests the {@link FollowingService#fetchFollowing(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testAddPost_nullRequest_throwsException() throws IOException {

        Assertions.assertThrows(NullPointerException.class, () -> {
            postServiceSpy.addPost(null);
        });
    }

    /**
     * Verify that for failed requests the {@link FollowingService#fetchFollowing(FollowRequest)}
     * method throws an exception.
     */
    @Test
    public void testAddPost_invalidRequest_missingAlias_throwsException() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            postServiceSpy.addPost(invalidRequest);
        });
    }

    /**
     * Verify that for failed requests the {@link FollowingService#fetchFollowing(FollowRequest)}
     * method returns a bad response..
     */
    @Test
    public void testAddPost_invalidRequest_returnsResponse() throws IOException {
        Assertions.assertEquals(failureResponse, postServiceSpy.addPost(otherInvalidRequest));
    }
}
