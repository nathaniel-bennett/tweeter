package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.server.dao.PostDAO;
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

public class PostServiceTests {

    PostServiceImpl service;
    PostDAO dao;
    PostRequest goodRequest;
    PostResponse goodResponse;
    PostRequest badRequest;
    PostResponse badResponse;

    @BeforeEach
    public void setup() {

        dao = Mockito.mock(PostDAO.class);
        service = Mockito.spy(PostServiceImpl.class);

        goodRequest = new PostRequest("HELLO", "@me", new AuthToken());
        goodResponse = new PostResponse("ok");
        Mockito.when(dao.addToStory(goodRequest)).thenReturn(goodResponse);

        badRequest = new PostRequest("Bad", "@fake", new AuthToken());
        badResponse = new PostResponse("failure");
        Mockito.when(dao.addToStory(badRequest)).thenReturn(badResponse);

        Mockito.when(service.getPostDAO()).thenReturn(dao);

    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new PostRequest("Hello there", null, new AuthToken())));
        args.add(Arguments.of(new PostRequest("Hello there", "", new AuthToken())));
        args.add(Arguments.of(new PostRequest("Hello there", "@me", null)));


        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput")
    public void testBadInputThrowsException(PostRequest request) {

        if (request.getAuthToken() != null) {
            Assertions.assertThrows(BadRequestException.class, () -> {
                service.addPost(request);
            });
        } else {
            Assertions.assertThrows(NotAuthorizedException.class, () -> {
                service.addPost(request);
            });
        }

    }

    @Test
    public void testServiceReturnsCorrectObject() {
        PostResponse response = service.addPost(goodRequest);
        Assertions.assertTrue(response == goodResponse);

        response = service.addPost(badRequest);
        Assertions.assertTrue(response == badResponse);
    }
}
