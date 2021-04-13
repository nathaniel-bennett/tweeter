package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.server.dao.FollowDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.model.StoredUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FollowingServiceTests {

    FollowingServiceImpl followingService;
    FollowDAO followDAO;
    UserDAO userDAO;
    List<String> alias;
    FollowRequest goodRequest;
    FollowResponse goodResponse;



    @BeforeEach
    public void setup() {
        followDAO = Mockito.mock(FollowDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        followingService = Mockito.spy(FollowingServiceImpl.class);
        alias = Arrays.asList("sallyD", "hankD");

        goodRequest = new FollowRequest("HELLO", 5, "FollowME");
        goodResponse = new FollowResponse(null, false);
        Mockito.when(followDAO.getFollowedBy(goodRequest.getFollowAlias(), goodRequest.getLimit(), goodRequest.getLastFollowAlias())).thenReturn(alias);
        Mockito.when(userDAO.getUser(alias.get(0))).thenReturn(new StoredUser("good", "response", "password", "hello", "", 0, 0));
        Mockito.when(userDAO.getUser(alias.get(1))).thenReturn(new StoredUser("good", "response", "password", "hello", "", 0, 0));
    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new FollowRequest(null, 1, "@me")));
        args.add(Arguments.of(new FollowRequest("", 1, "@me")));
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
        Assertions.assertNotEquals(0, response.getRequestedUsers());
        Assertions.assertFalse(response.getHasMorePages());
    }
}
