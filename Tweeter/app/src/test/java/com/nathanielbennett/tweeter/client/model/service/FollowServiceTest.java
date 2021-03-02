package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.service.FollowService;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

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

    @Nested
    class UnfollowUserResponseTests {

        private UnfollowUserRequest nullUsername;
        private UnfollowUserRequest nullAuthToken;
        private UnfollowUserRequest nullUserToUnfollow;
        private UnfollowUserRequest badRequest;
        private UnfollowUserRequest goodRequest;

        private UnfollowUserResponse badResponse;
        private UnfollowUserResponse goodResponse;

        @BeforeEach
        public void setupFunction() {

            nullUsername = new UnfollowUserRequest(null, new AuthToken(), "@Me");
            nullAuthToken = new UnfollowUserRequest("@Me", null, "@Me2");
            nullUserToUnfollow = new UnfollowUserRequest("@Me", new AuthToken(), null);
            badRequest = new UnfollowUserRequest("@BadUser", new AuthToken(), "@WorseUser");
            goodRequest = new UnfollowUserRequest("@GoodUser", new AuthToken(), "@BetterUser");

            badResponse = new UnfollowUserResponse("Sorry, wasn't legit!");
            goodResponse = new UnfollowUserResponse("Success!");

            Mockito.when(mockServerFacade.unfollow(badRequest)).thenReturn(badResponse);
            Mockito.when(mockServerFacade.unfollow(goodRequest)).thenReturn(goodResponse);
        }

        /**
         * Verifies that when a null request is passed into {@link FollowService#unfollow(UnfollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testUnfollowUser_nullRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.unfollow(null);
            });
        }

        /**
         * Verifies that when a request with a nullUsername is passed into {@link FollowService#unfollow(UnfollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testUnfollowUser_nullUsername_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.unfollow(nullUsername);
            });
        }

        /**
         * Verifies that when a request with a nullAuthToken is passed into {@link FollowService#unfollow(UnfollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testUnfollowUser_nullAuthToken_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.unfollow(nullAuthToken);
            });
        }

        /**
         * Verifies that when a request with a nullUserToUnfollow is passed into {@link FollowService#unfollow(UnfollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testUnfollowUser_nullUserToUnfollow_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.unfollow(nullUserToUnfollow);
            });
        }

        /**
         * Verifies that when a bad is passed into {@link FollowService#unfollow(UnfollowUserRequest)}
         * the correct response from the service is passed back.
         */
        @Test
        public void testUnfollowUser_badRequest_correctResponse() throws IOException {
            Assertions.assertEquals(badResponse, followServiceSpy.unfollow(badRequest));
        }

        /**
         * Verifies that when a bad is passed into {@link FollowService#unfollow(UnfollowUserRequest)}
         * the correct response from the service is passed back.
         */
        @Test
        public void testUnfollowUser_validRequest_correctResponse() throws IOException {
            Assertions.assertEquals(goodResponse, followServiceSpy.unfollow(goodRequest));
        }

    }

    @Nested
    class CheckFollowStatusTest {

        private CheckFollowingRequest nullUsername;
        private CheckFollowingRequest nullOtherUser;
        private CheckFollowingRequest nullAuthToken;
        private CheckFollowingRequest badRequest;
        private CheckFollowingRequest validRequest;

        private CheckFollowingResponse badResponse;
        private CheckFollowingResponse validResponse;

        @BeforeEach
        public void setupFunction() {

            nullUsername = new CheckFollowingRequest(null, new AuthToken(), "@Other");
            nullOtherUser = new CheckFollowingRequest("@Good", new AuthToken(), null);
            nullAuthToken = new CheckFollowingRequest("@Hello", null, "@Other");
            badRequest = new CheckFollowingRequest("@BadGuy", new AuthToken(), "@BadGirl");
            validRequest = new CheckFollowingRequest("@GoodGuy", new AuthToken(), "@GoodGirl");

            badResponse = new CheckFollowingResponse("User not found");
            validResponse = new CheckFollowingResponse(true);

            Mockito.when(mockServerFacade.isFollowing(badRequest)).thenReturn(badResponse);
            Mockito.when(mockServerFacade.isFollowing(validRequest)).thenReturn(validResponse);
        }

        /**
         * Verifies that when a null request is passed into {@link FollowService#checkFollowStatus(CheckFollowingRequest)}
         * throws a Null Pointer Exception.
         */
        @Test
        public void testCheckFollowStatus_nullRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
               followServiceSpy.checkFollowStatus(null);
            });
        }

        /**
         * Verifies that when a request with a null username is passed into {@link FollowService#checkFollowStatus(CheckFollowingRequest)}
         * throws a Null Pointer Exception.
         */
        @Test
        public void testCheckFollowStatus_nullUsername_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.checkFollowStatus(nullUsername);
            });
        }

        /**
         * Verifies that when a request with a null otherUser is passed into {@link FollowService#checkFollowStatus(CheckFollowingRequest)}
         * throws a Null Pointer Exception.
         */
        @Test
        public void testCheckFollowStatus_nullOtherUser_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.checkFollowStatus(nullOtherUser);
            });
        }

        /**
         * Verifies that when a request with a null authToken is passed into {@link FollowService#checkFollowStatus(CheckFollowingRequest)}
         * throws a Null Pointer Exception.
         */
        @Test
        public void testCheckFollowStatus_nullAuthToken_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceSpy.checkFollowStatus(nullAuthToken);
            });
        }

        /**
         * Verifies that when a bad request is passed into {@link FollowService#checkFollowStatus(CheckFollowingRequest)}
         * the service returns the correct result
         */
        @Test
        public void testCheckFollowStatus_badRequest_correctResponse() throws IOException {
            Assertions.assertEquals(badResponse, followServiceSpy.checkFollowStatus(badRequest));
        }

        /**
         * Verifies that when a valid request is passed into {@link FollowService#checkFollowStatus(CheckFollowingRequest)}
         * the service returns the correct result
         */
        @Test
        public void testCheckFollowStatus_validRequest_correctResponse() throws IOException {
            Assertions.assertEquals(validResponse, followServiceSpy.checkFollowStatus(validRequest));
        }
    }

}
