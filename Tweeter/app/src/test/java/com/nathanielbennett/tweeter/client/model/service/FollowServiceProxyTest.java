package com.nathanielbennett.tweeter.client.model.service;

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

public class FollowServiceProxyTest {

    private FollowServiceProxy followServiceProxySpy;
    private ServerFacade mockServerFacade;


    @BeforeEach
    public void setup() {

        mockServerFacade = Mockito.mock(ServerFacade.class);
        followServiceProxySpy = Mockito.spy(new FollowServiceProxy());
        Mockito.when(followServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
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
        public void setupFunction() throws IOException {

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
         * Verifies that when a null request is passed into {@link FollowServiceProxy#follow(FollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testFollowUser_nullRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.follow(null);
            });
        }

        /**
         * Verifies that when a request with a null username is passed into
         * {@link FollowServiceProxy#follow(FollowUserRequest)} a Null Pointer Exception is thrown.
         */
        @Test
        public void testFollowUser_nullUserNameRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.follow(nullUserName);
            });
        }

        /**
         * Verifies that when a request with a null authToken is passed into
         * {@link FollowServiceProxy#follow(FollowUserRequest)} a Null Pointer Exception is thrown.
         */
        @Test
        public void testFollowUser_nullAuthTokenRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.follow(nullAuthToken);
            });
        }

        /**
         * Verifies that when a request with a null userToFollow is passed into
         * {@link FollowServiceProxy#follow(FollowUserRequest)} a Null Pointer Exception is thrown.
         */
        @Test
        public void testFollowUser_nullUserToFollowRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.follow(nullUserToFollow);
            });
        }

        /**
         * Verifies that when a bad request is passed into {@link FollowServiceProxy#follow(FollowUserRequest)}
         * the correct response is passed back.
         */
        @Test
        public void testFollowUser_badRequest_tryToFollowSelf_correctResponse() throws IOException {
            Assertions.assertEquals("Error - too much vanity (you can't follow yourself)",
                    followServiceProxySpy.follow(followSelfRequest).getErrorMessage());
        }

        /**
         * Verifies that when a bad request is passed into {@link FollowServiceProxy#follow(FollowUserRequest)}
         * the response from the server is passed back.
         */
        @Test
        public void testFollowUser_badRequest_correctResponse() throws IOException {
            Assertions.assertEquals(failureResponse, followServiceProxySpy.follow(badRequest));
        }

        /**
         * Verifies that when a valid request is passed into {@link FollowServiceProxy#follow(FollowUserRequest)}
         * the response from the server is passed back.
         */
        @Test
        public void testFollowUser_validRequest_correctResponse() throws IOException {
            Assertions.assertEquals(successResponse, followServiceProxySpy.follow(validRequest));
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
        public void setupFunction() throws IOException {

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
         * Verifies that when a null request is passed into {@link FollowServiceProxy#unfollow(UnfollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testUnfollowUser_nullRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.unfollow(null);
            });
        }

        /**
         * Verifies that when a request with a nullUsername is passed into {@link FollowServiceProxy#unfollow(UnfollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testUnfollowUser_nullUsername_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.unfollow(nullUsername);
            });
        }

        /**
         * Verifies that when a request with a nullAuthToken is passed into {@link FollowServiceProxy#unfollow(UnfollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testUnfollowUser_nullAuthToken_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.unfollow(nullAuthToken);
            });
        }

        /**
         * Verifies that when a request with a nullUserToUnfollow is passed into {@link FollowServiceProxy#unfollow(UnfollowUserRequest)}
         * a Null Pointer Exception is thrown.
         */
        @Test
        public void testUnfollowUser_nullUserToUnfollow_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.unfollow(nullUserToUnfollow);
            });
        }

        /**
         * Verifies that when a bad is passed into {@link FollowServiceProxy#unfollow(UnfollowUserRequest)}
         * the correct response from the service is passed back.
         */
        @Test
        public void testUnfollowUser_badRequest_correctResponse() throws IOException {
            Assertions.assertEquals(badResponse, followServiceProxySpy.unfollow(badRequest));
        }

        /**
         * Verifies that when a bad is passed into {@link FollowServiceProxy#unfollow(UnfollowUserRequest)}
         * the correct response from the service is passed back.
         */
        @Test
        public void testUnfollowUser_validRequest_correctResponse() throws IOException {
            Assertions.assertEquals(goodResponse, followServiceProxySpy.unfollow(goodRequest));
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
        public void setupFunction() throws IOException {

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
         * Verifies that when a null request is passed into {@link FollowServiceProxy#checkFollowStatus(CheckFollowingRequest)}
         * throws a Null Pointer Exception.
         */
        @Test
        public void testCheckFollowStatus_nullRequest_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
               followServiceProxySpy.checkFollowStatus(null);
            });
        }

        /**
         * Verifies that when a request with a null username is passed into {@link FollowServiceProxy#checkFollowStatus(CheckFollowingRequest)}
         * throws a Null Pointer Exception.
         */
        @Test
        public void testCheckFollowStatus_nullUsername_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.checkFollowStatus(nullUsername);
            });
        }

        /**
         * Verifies that when a request with a null otherUser is passed into {@link FollowServiceProxy#checkFollowStatus(CheckFollowingRequest)}
         * throws a Null Pointer Exception.
         */
        @Test
        public void testCheckFollowStatus_nullOtherUser_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.checkFollowStatus(nullOtherUser);
            });
        }

        /**
         * Verifies that when a request with a null authToken is passed into {@link FollowServiceProxy#checkFollowStatus(CheckFollowingRequest)}
         * throws a Null Pointer Exception.
         */
        @Test
        public void testCheckFollowStatus_nullAuthToken_throwsException() {
            Assertions.assertThrows(NullPointerException.class, () -> {
                followServiceProxySpy.checkFollowStatus(nullAuthToken);
            });
        }

        /**
         * Verifies that when a bad request is passed into {@link FollowServiceProxy#checkFollowStatus(CheckFollowingRequest)}
         * the service returns the correct result
         */
        @Test
        public void testCheckFollowStatus_badRequest_correctResponse() throws IOException {
            Assertions.assertEquals(badResponse, followServiceProxySpy.checkFollowStatus(badRequest));
        }

        /**
         * Verifies that when a valid request is passed into {@link FollowServiceProxy#checkFollowStatus(CheckFollowingRequest)}
         * the service returns the correct result
         */
        @Test
        public void testCheckFollowStatus_validRequest_correctResponse() throws IOException {
            Assertions.assertEquals(validResponse, followServiceProxySpy.checkFollowStatus(validRequest));
        }
    }

}
