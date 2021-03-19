package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.server.dao.FollowingDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;

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

public class FollowingServiceTests {

    FollowingServiceImpl followingService;
    FollowingDAO followDAO;
    FollowRequest goodRequest;
    FollowResponse goodResponse;
    FollowRequest badRequest;
    FollowResponse badResponse;

    @BeforeEach
    public void setup() {
        followDAO = Mockito.mock(FollowingDAO.class);
        followingService = Mockito.spy(FollowingServiceImpl.class);

        goodRequest = new FollowRequest("HELLO", 5, "FollowME");
        goodResponse = new FollowResponse(null, false);
        Mockito.when(followDAO.getFollowing(goodRequest)).thenReturn(goodResponse);

        badRequest = new FollowRequest("HELLO", 4, "FollowME");
        badResponse = new FollowResponse("failure");
        Mockito.when(followDAO.getFollowing(badRequest)).thenReturn(badResponse);

        Mockito.when(followingService.getFollowingDAO()).thenReturn(followDAO);
    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new FollowRequest(null, 1, "@me")));
        args.add(Arguments.of(new FollowRequest("", 1, "@me")));
        args.add(Arguments.of(new FollowRequest("@me", 1, "")));
        args.add(Arguments.of(new FollowRequest("@me", 1, null)));
        args.add(Arguments.of(new FollowRequest("@you", 0, "@me")));

        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput")
    public void testBadInputThrowsException(FollowRequest request) {
        Assertions.assertThrows(BadRequestException.class, () -> {
            followingService.getFollowees(request);
        });
    }

    @Test
    public void testServiceReturnsCorrectObject() {
        FollowResponse response = followingService.getFollowees(goodRequest);
        Assertions.assertTrue(response == goodResponse);

        response = followingService.getFollowees(badRequest);
        Assertions.assertTrue(response == badResponse);
    }
}
