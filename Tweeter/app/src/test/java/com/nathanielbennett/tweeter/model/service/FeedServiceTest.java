package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FeedServiceTest {

    private StatusRequest validRequest;
    private StatusRequest invalidRequest;

    private StatusResponse successResponse;
    private StatusResponse failureResponse;

    private FeedService feedServiceSpy;


    @BeforeEach
    public void setup() {
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
        validRequest = new StatusRequest(currentUser, 1, null);
        invalidRequest = new StatusRequest(null, 10, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusResponse(true, Arrays.asList(hisStatus));
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest)).thenReturn(successResponse);

        failureResponse = new StatusResponse("Bad data passed in");
        Mockito.when(mockServerFacade.getFeed(invalidRequest)).thenReturn(failureResponse);

        // Create a FeedService instance and wrap it with a spy that will use the mock service.
        feedServiceSpy = Mockito.spy(new FeedService());
        Mockito.when(feedServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link FeedService#fetchFeed(StatusRequest)} method
     * returns the saem result as the {@link ServerFacade}
     *
     * @throws IOException
     */
    @Test
    public void testFetchFeed_validRequest_correctResponse() throws IOException {
        StatusResponse response = feedServiceSpy.fetchFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testFetchFeed_nullRequest_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            feedServiceSpy.fetchFeed(null);
        });
    }

    @Test
    public void testFetchFeed_invalidRequest_throwsException() throws IOException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            feedServiceSpy.fetchFeed(invalidRequest);
        });
    }

}
