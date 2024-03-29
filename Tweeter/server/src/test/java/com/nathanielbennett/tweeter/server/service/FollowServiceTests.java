package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.AuthorizationRequest;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.AuthorizationResponse;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
import com.nathanielbennett.tweeter.server.dao.FollowDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FollowServiceTests {


    FollowServiceImpl followService;
    FollowDAO followDAO;
    UserDAO userDAO;
    AuthorizationServiceImpl authService;
    FollowUserRequest goodRequest1;
    FollowUserResponse goodResponse1;
    UnfollowUserRequest goodRequest2;
    UnfollowUserResponse goodResponse2;
    CheckFollowingRequest goodRequest3;
    CheckFollowingResponse goodResponse3;


    @BeforeEach
    public void setup() {
        followDAO = Mockito.mock(FollowDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        followService = Mockito.spy(FollowServiceImpl.class);
        authService = Mockito.spy(AuthorizationServiceImpl.class);

        // test follow() method
        goodRequest1 = new FollowUserRequest("HELLO", new AuthToken(), "FollowME");
        goodResponse1 = new FollowUserResponse();
        AuthorizationRequest request1 = new AuthorizationRequest(goodRequest1);
        Mockito.when(followService.followToAuthRequest(goodRequest1)).thenReturn(request1);
        Mockito.when(authService.isAuthorized(request1)).thenReturn(new AuthorizationResponse());

        // test unfollow() method
        goodRequest2 = new UnfollowUserRequest("HELLO", new AuthToken(), "FollowME");
        goodResponse2 = new UnfollowUserResponse();
        AuthorizationRequest request2 = new AuthorizationRequest(goodRequest2);
        Mockito.when(followService.unfollowToAuthRequest(goodRequest2)).thenReturn(request2);
        Mockito.when(authService.isAuthorized(request2)).thenReturn(new AuthorizationResponse());

        // test checkFollowStatus() method
        goodRequest3 = new CheckFollowingRequest("HELLO", new AuthToken(), "FollowME");
        goodResponse3 = new CheckFollowingResponse(true);
        AuthorizationRequest request3 = new AuthorizationRequest(goodRequest3);
        Mockito.when(followService.checkFollowToAuthRequest(goodRequest3)).thenReturn(request3);
        Mockito.when(authService.isAuthorized(request3)).thenReturn(new AuthorizationResponse());

        Mockito.when(followService.getFollowDAO()).thenReturn(followDAO);
        Mockito.when(followService.getUserDAO()).thenReturn(userDAO);
        Mockito.when(followService.getAuthService()).thenReturn(authService);
    }

    static Stream<Arguments> generateInvalidTestInput1() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new FollowUserRequest(null, null, "@me")));
        args.add(Arguments.of(new FollowUserRequest("", new AuthToken(), "@me")));
        args.add(Arguments.of(new FollowUserRequest("@me", null, "")));
        args.add(Arguments.of(new FollowUserRequest("@me", null, null)));

        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput1")
    public void testBadInputThrowsException1(FollowUserRequest request) {
        if (request.getAuthToken() == null){
            Assertions.assertThrows(NotAuthorizedException.class, () -> {
                followService.follow(request);
            });
        }
        else {
            Assertions.assertThrows(BadRequestException.class, () -> {
                followService.follow(request);
            });
        }
    }

    @Test
    public void testServiceReturnsCorrectObject1() {
        FollowUserResponse response = followService.follow(goodRequest1);
        Assertions.assertTrue(response.getSuccess());
    }

    static Stream<Arguments> generateInvalidTestInput2() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new UnfollowUserRequest(null, null, "@me")));
        args.add(Arguments.of(new UnfollowUserRequest("", new AuthToken(), "@me")));
        args.add(Arguments.of(new UnfollowUserRequest("@me", null, "")));
        args.add(Arguments.of(new UnfollowUserRequest("@me", null, null)));

        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput2")
    public void testBadInputThrowsException2(UnfollowUserRequest request) {
        if (request.getAuthToken() == null){
            Assertions.assertThrows(NotAuthorizedException.class, () -> {
                followService.unfollow(request);
            });
        }
        else {
            Assertions.assertThrows(BadRequestException.class, () -> {
                followService.unfollow(request);
            });
        }
    }

    @Test
    public void testServiceReturnsCorrectObject2() {
        UnfollowUserResponse response = followService.unfollow(goodRequest2);
        Assertions.assertTrue(response.getSuccess());
    }

    static Stream<Arguments> generateInvalidTestInput3() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new CheckFollowingRequest(null, null, "@me")));
        args.add(Arguments.of(new CheckFollowingRequest("", new AuthToken(), "@me")));
        args.add(Arguments.of(new CheckFollowingRequest("@me", null, "")));
        args.add(Arguments.of(new CheckFollowingRequest("@me", null, null)));

        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput3")
    public void testBadInputThrowsException3(CheckFollowingRequest request) {
        if (request.getAuthToken() == null){
            Assertions.assertThrows(NotAuthorizedException.class, () -> {
                followService.checkFollowStatus(request);
            });
        }
        else {
            Assertions.assertThrows(BadRequestException.class, () -> {
                followService.checkFollowStatus(request);
            });
        }
    }

    @Test
    public void testServiceReturnsCorrectObject3() {
        CheckFollowingResponse response = followService.checkFollowStatus(goodRequest3);
        Assertions.assertTrue(response.getSuccess());
    }

}
