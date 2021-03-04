package com.nathanielbennett.tweeter.client.model.service;

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

public class FollowingServiceTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;
    private FollowRequest otherInvalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowingServiceProxy followingServiceSpy;


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
        Mockito.when(mockServerFacade.getFollowing(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowResponse("No users found");
        Mockito.when(mockServerFacade.getFollowing(otherInvalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new FollowingServiceProxy());
        Mockito.when(followingServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException {
        FollowResponse response = followingServiceSpy.getFollowees(validRequest);
        Assertions.assertEquals(successResponse, response);
    }




    /**
     * Verify that for failed requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testFetchFollowing_nullRequest_throwsException() throws IOException {

        Assertions.assertThrows(NullPointerException.class, () -> {
            followingServiceSpy.getFollowees(null);
        });
    }

    /**
     * Verify that for failed requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method throws an exception.
     */
    @Test
    public void testFetchFollowing_invalidRequest_missingAlias_throwsException() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            followingServiceSpy.getFollowees(invalidRequest);
        });
    }

    /**
     * Verify that for failed requests the {@link FollowingServiceProxy#getFollowees(FollowRequest)}
     * method returns a bad response..
     */
    @Test
    public void testFetchFollowing_invalidRequest_returnsResponse() throws IOException {

        Assertions.assertEquals(failureResponse, followingServiceSpy.getFollowees(otherInvalidRequest));
    }
}
