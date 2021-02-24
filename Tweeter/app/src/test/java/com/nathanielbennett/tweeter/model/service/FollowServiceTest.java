package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class FollowServiceTest {

    private FollowService followServiceSpy;
    private ServerFacade mockServerFacade;


    @BeforeEach
    public void setup() {

        mockServerFacade = Mockito.mock(ServerFacade.class);
        followServiceSpy = Mockito.spy(new FollowService());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Nested
    class followUserTests {

        private FollowUserRequest nullUserName;
        private FollowUserRequest nullAuthToken;
        private FollowUserRequest nullUserToFollow;
        private FollowUserRequest followSelfRequest;
        private FollowUserRequest badRequest;
        private FollowUserRequest validRequest;

        private FollowUserResponse failureResponse;
        private FollowUserResponse successResponse;

        @BeforeEach
        public void setupFunction() {

            nullUserName = new FollowUserRequest(null, new AuthToken(), "@HelloThere");
            nullAuthToken = new FollowUserRequest("@GenHammer", null, "@HelloThere");
            nullUserToFollow = new FollowUserRequest("@Me", new AuthToken(), null);
            followSelfRequest = new FollowUserRequest("@ItME", new AuthToken(), "@ItME");
            badRequest = new FollowUserRequest("@BadGuy", new AuthToken(), "@BadGirl");
            validRequest = new FollowUserRequest("@GoodGuy", new AuthToken(), "@GoodGirl");

            failureResponse = new FollowUserResponse("Error - User not found");
            successResponse = new FollowUserResponse("Success!!!");

            Mockito.when(mockServerFacade.follow(badRequest)).thenReturn(failureResponse);
            Mockito.when(mockServerFacade.follow(validRequest)).thenReturn(successResponse);
        }


        /**
         * Verifies that when a null request is passed into {@link FollowService#follow(FollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testFollowUser_nullRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.follow(null);
            });
        }

        /**
         * Verifies that when a request with a null username is passed into
         * {@link FollowService#follow(FollowUserRequest)} a Null Pointer Exception is thrown.
         */
        @Test
        public void testFollowUser_nullUserNameRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.follow(nullUserName);
            });
        }

        /**
         * Verifies that when a request with a null authToken is passed into
         * {@link FollowService#follow(FollowUserRequest)} a Null Pointer Exception is thrown.
         */
        @Test
        public void testFollowUser_nullAuthTokenRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.follow(nullAuthToken);
            });
        }

        /**
         * Verifies that when a request with a null userToFollow is passed into
         * {@link FollowService#follow(FollowUserRequest)} a Null Pointer Exception is thrown.
         */
        @Test
        public void testFollowUser_nullUserToFollowRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.follow(nullUserToFollow);
            });
        }

        /**
         * Verifies that when a bad request is passed into {@link FollowService#follow(FollowUserRequest)}
         * the correct response is passed back.
         */
        @Test
        public void testFollowUser_badRequest_tryToFollowSelf_correctResponse() throws IOException {
            Assertions.assertEquals("Error - too much vanity (you can't follow yourself)",
                    followServiceSpy.follow(followSelfRequest).getMessage());
        }

        /**
         * Verifies that when a bad request is passed into {@link FollowService#follow(FollowUserRequest)}
         * the response from the server is passed back.
         */
        @Test
        public void testFollowUser_badRequest_correctResponse() throws IOException {
            Assertions.assertEquals(failureResponse, followServiceSpy.follow(badRequest));
        }

        /**
         * Verifies that when a valid request is passed into {@link FollowService#follow(FollowUserRequest)}
         * the response from the server is passed back.
         */
        @Test
        public void testFollowUser_validRequest_correctResponse() throws IOException {
            Assertions.assertEquals(successResponse, followServiceSpy.follow(validRequest));
        }

    }

}
