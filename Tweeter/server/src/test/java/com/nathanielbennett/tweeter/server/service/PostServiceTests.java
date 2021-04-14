package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.AuthorizationRequest;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.AuthorizationResponse;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.server.dao.StoryDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PostServiceTests {

    PostServiceImpl service;
    StoryDAO dao;
    AuthorizationServiceImpl authService;
    PostRequest goodRequest;
    PostResponse goodResponse;

    @BeforeEach
    public void setup() {

        dao = Mockito.mock(StoryDAO.class);
        authService = Mockito.mock(AuthorizationServiceImpl.class);
        service = Mockito.spy(PostServiceImpl.class);

        goodRequest = new PostRequest("HELLO", "@me", new AuthToken());
        goodResponse = new PostResponse("ok");

        StoredStatus storedStatus =  new StoredStatus(goodRequest.getUsername(), goodRequest.getStatus(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        AuthorizationRequest request1 = new AuthorizationRequest(goodRequest);
        Mockito.when(service.postToStoredStatus(goodRequest)).thenReturn(storedStatus);
        Mockito.when(service.getAuthService()).thenReturn(authService);
        Mockito.when(service.postToAuthRequest(goodRequest)).thenReturn(request1);
        Mockito.when(authService.isAuthorized(request1)).thenReturn(new AuthorizationResponse());
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
        Assertions.assertTrue(response.getSuccess());
    }
}
