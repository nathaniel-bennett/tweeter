package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class PostServiceProxyTest {

    private PostRequest validRequest;
    private PostRequest invalidRequest;
    private PostRequest otherInvalidRequest;
    private PostRequest nullAuthToken;

    private PostResponse successResponse;
    private PostResponse failureResponse;

    private PostServiceProxy postServiceProxySpy;


    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        validRequest = new PostRequest("THis is a status", "FirstNameLastName", new AuthToken());
        invalidRequest = new PostRequest(null, null, null);
        otherInvalidRequest = new PostRequest("abcdefg", "philberta", new AuthToken());
        nullAuthToken = new PostRequest("hello", "philberta", null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.addToStory(validRequest)).thenReturn(successResponse);

        failureResponse = new PostResponse("No users found");
        Mockito.when(mockServerFacade.addToStory(otherInvalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        postServiceProxySpy = Mockito.spy(new PostServiceProxy());
        Mockito.when(postServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */

    @Test
    public void testAddPost_validRequest_correctResponse() throws IOException {
        PostResponse response = postServiceProxySpy.addPost(validRequest);
        Assertions.assertEquals(successResponse, response);
    }




    /**
     * Verify that for failed requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testAddPost_nullRequest_throwsException() throws IOException {

        Assertions.assertThrows(NullPointerException.class, () -> {
            postServiceProxySpy.addPost(null);
        });
    }

    /**
     * Verify that for failed requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method throws an exception.
     */
    @Test
    public void testAddPost_invalidRequest_missingAlias_throwsException() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            postServiceProxySpy.addPost(invalidRequest);
        });
    }

    /**
     * Verify that for failed requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method throws an exception.
     */
    @Test
    public void testAddPost_invalidRequest_missingAuthToken_throwsException() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            postServiceProxySpy.addPost(nullAuthToken);
        });
    }

    /**
     * Verify that for failed requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method returns a bad response..
     */
    @Test
    public void testAddPost_invalidRequest_returnsResponse() throws IOException {
        Assertions.assertEquals(failureResponse, postServiceProxySpy.addPost(otherInvalidRequest));
    }
}
