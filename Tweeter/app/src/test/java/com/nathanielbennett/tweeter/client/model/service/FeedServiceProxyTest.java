package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FeedServiceProxyTest {

    private StatusRequest validRequest;
    private StatusRequest invalidRequest;
    private StatusRequest otherInvalidRequest;

    private StatusResponse successResponse;
    private StatusResponse failureResponse;

    private FeedServiceProxy feedServiceProxySpy;


    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        Status myStatus = new Status(currentUser, "Hello there", "Today", new ArrayList<>());
        Status hisStatus = new Status(resultUser1, "Good bye", "Tomorrow", new ArrayList<>());
        Status herStatus = new Status(resultUser2, "Thinking of @FirstNameLastName right now",
                "Yesterday", Arrays.asList(currentUser));

        // Setup request objects to use in the tests
        validRequest = new StatusRequest(currentUser.getAlias(), 1, null);
        invalidRequest = new StatusRequest(null, 10, null);
        otherInvalidRequest = new StatusRequest(resultUser2.getAlias(), 3, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusResponse(true, Arrays.asList(hisStatus));
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest)).thenReturn(successResponse);

        failureResponse = new StatusResponse("Bad data passed in");
        Mockito.when(mockServerFacade.getFeed(otherInvalidRequest)).thenReturn(failureResponse);

        // Create a FeedService instance and wrap it with a spy that will use the mock service.
        feedServiceProxySpy = Mockito.spy(new FeedServiceProxy());
        Mockito.when(feedServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link FeedServiceProxy#fetchFeed(StatusRequest)} method
     * returns the saem result as the {@link ServerFacade}
     *
     * @throws IOException
     */
    @Test
    public void testFetchFeed_validRequest_correctResponse() throws IOException {
        StatusResponse response = feedServiceProxySpy.fetchFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for a null request the {@link FeedServiceProxy#fetchFeed(StatusRequest)} throws a
     * null pointer exception.
     */
    @Test
    public void testFetchFeed_nullRequest_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            feedServiceProxySpy.fetchFeed(null);
        });
    }

    /**
     * Verify that for a bad request the {@link FeedServiceProxy#fetchFeed(StatusRequest)} throws a
     * null pointer exception.
     */
    @Test
    public void testFetchFeed_invalidRequest_throwsException() throws IOException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            feedServiceProxySpy.fetchFeed(invalidRequest);
        });
    }

    /**
     * Verify that for failed request the {@link FeedServiceProxy#fetchFeed(StatusRequest)} returns the
     * bad response from the server.
     */
    @Test
    public void testFetchFeed_invalidRequest__correctRespoinse() throws IOException {
        StatusResponse response = feedServiceProxySpy.fetchFeed(otherInvalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}
