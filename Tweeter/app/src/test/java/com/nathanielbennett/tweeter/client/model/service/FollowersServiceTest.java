package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.service.FollowersService;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class FollowersServiceTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;
    private FollowRequest otherInvalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowersService followersService;


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
        validRequest = new FollowRequest(currentUser.getAlias(), 3, null);
        invalidRequest = new FollowRequest(null, 0, null);
        otherInvalidRequest = new FollowRequest("Dunder", 3, "Miflin");

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowers(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowResponse("No users found");
        Mockito.when(mockServerFacade.getFollowers(otherInvalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followersService = Mockito.spy(new FollowersService());
        Mockito.when(followersService.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for a null request the {@link FollowersService#fetchFollowers(FollowRequest)}
     * throws a null pointer exception.
     */
    @Test
    public void testGetFollowers_nullRequest_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            followersService.fetchFollowers(null);
        });
    }

    /**
     * Verify that for an invalid request the {@link FollowersService#fetchFollowers(FollowRequest)}
     * throws a null pointer exception.
     */
    @Test
    public void testGetFollowers_badRequest_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
           followersService.fetchFollowers(invalidRequest);
        });
    }

    /**
     * Verify that for a bad request the {@link FollowersService#fetchFollowers(FollowRequest)}
     * returns the response from the server facade.
     */
    @Test
    public void testGetFollowers_badRequest_correctResponse() throws IOException {
        Assertions.assertEquals(failureResponse, followersService.fetchFollowers(otherInvalidRequest));
    }

    /**
     * Verify that for a successful request the {@link FollowersService#fetchFollowers(FollowRequest)}
     * returns the response from the server facade.
     */
    @Test
    public void testGetFollowers_goodRequest_correctResponse() throws IOException {
        Assertions.assertEquals(successResponse, followersService.fetchFollowers(validRequest));
    }
}
