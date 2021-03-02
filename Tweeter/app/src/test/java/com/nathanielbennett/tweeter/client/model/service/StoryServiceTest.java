package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.service.StoryService;
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

public class StoryServiceTest {

    private StatusRequest validRequest;
    private StatusRequest invalidRequest;
    private StatusRequest otherInvalidRequest;

    private StatusResponse successResponse;
    private StatusResponse failureResponse;

    private StoryService storyServiceSpy;


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
        otherInvalidRequest = new StatusRequest(resultUser2, 3, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusResponse(true, Arrays.asList(hisStatus));
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStory(validRequest)).thenReturn(successResponse);

        failureResponse = new StatusResponse("Bad data passed in");
        Mockito.when(mockServerFacade.getStory(otherInvalidRequest)).thenReturn(failureResponse);

        // Create a StoryService instance and wrap it with a spy that will use the mock service.
        storyServiceSpy = Mockito.spy(new StoryService());
        Mockito.when(storyServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link StoryService#fetchStory(StatusRequest)} method
     * returns the same result as the {@link ServerFacade}
     *
     * @throws IOException
     */
    @Test
    public void testFetchStory_validRequest_correctResponse() throws IOException {
        StatusResponse response = storyServiceSpy.fetchStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for a null request the {@link StoryService#fetchStory(StatusRequest)} throws a
     * null pointer exception.
     */
    @Test
    public void testFetchStory_nullRequest_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            storyServiceSpy.fetchStory(null);
        });
    }

    /**
     * Verify that for a bad request the {@link StoryService#fetchStory(StatusRequest)} throws a
     * null pointer exception.
     */
    @Test
    public void testFetchStory_invalidRequest_throwsException() throws IOException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            storyServiceSpy.fetchStory(invalidRequest);
        });
    }

    /**
     * Verify that for failed request the {@link StoryService#fetchStory(StatusRequest)} returns the
     * bad response from the server.
     */
    @Test
    public void testFetchStory_invalidRequest__correctResponse() throws IOException {
        StatusResponse response = storyServiceSpy.fetchStory(otherInvalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}
