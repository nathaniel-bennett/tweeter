package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.dao.FeedDAO;
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

public class FeedServiceTests {

    FeedServiceImpl feedService;
    FeedDAO feedDAO;
    StatusRequest goodRequest;
    StatusResponse goodResponse;
    StatusRequest badRequest;
    StatusResponse badResponse;

    @BeforeEach
    public void setup() {
        feedDAO = Mockito.mock(FeedDAO.class);
        feedService = Mockito.spy(FeedServiceImpl.class);

        goodRequest = new StatusRequest("@me", 10, "hello");
        goodResponse = new StatusResponse(false, null);
        Mockito.when(feedDAO.getFeed(goodRequest)).thenReturn(goodResponse);

        badRequest = new StatusRequest("HELLO", 4, "FollowME");
        badResponse = new StatusResponse("failure");
        Mockito.when(feedDAO.getFeed(badRequest)).thenReturn(badResponse);

        Mockito.when(feedService.getFeedDao()).thenReturn(feedDAO);
    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new StatusRequest(null, 1, "@me")));
        args.add(Arguments.of(new StatusRequest("", 1, "@me")));

        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput")
    public void testBadInputThrowsException(StatusRequest request) {
        Assertions.assertThrows(BadRequestException.class, () -> {
            feedService.fetchFeed(request);
        });
    }

    @Test
    public void testServiceReturnsCorrectObject() {
        StatusResponse response = feedService.fetchFeed(goodRequest);
        Assertions.assertTrue(response == goodResponse);

        response = feedService.fetchFeed(badRequest);
        Assertions.assertTrue(response == badResponse);
    }
}
