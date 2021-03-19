package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.dao.StoryDAO;
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

public class StoryServiceTests {

    StoryServiceImpl service;
    StoryDAO dao;
    StatusRequest goodRequest;
    StatusResponse goodResponse;
    StatusRequest badRequest;
    StatusResponse badResponse;

    @BeforeEach
    public void setup() {

        dao = Mockito.mock(StoryDAO.class);
        service = Mockito.spy(StoryServiceImpl.class);

        goodRequest = new StatusRequest("@Hank",1,"hello there");
        goodResponse = new StatusResponse("ok");
        Mockito.when(dao.getStory(goodRequest)).thenReturn(goodResponse);

        badRequest = new StatusRequest("@Hank",1,"hello there");;
        badResponse = new StatusResponse("failure");
        Mockito.when(dao.getStory(badRequest)).thenReturn(badResponse);

        Mockito.when(service.getStoryDAO()).thenReturn(dao);

    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new StatusRequest(null,1,"hello there")));
        args.add(Arguments.of(new StatusRequest("",1,"hello there")));
        args.add(Arguments.of(new StatusRequest("@Hank",0,"hello there")));


        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput")
    public void testBadInputThrowsException(StatusRequest request) {

        Assertions.assertThrows(BadRequestException.class, () -> {
            service.fetchStory(request);
        });
    }

    @Test
    public void testServiceReturnsCorrectObject() {
        StatusResponse response = service.fetchStory(goodRequest);
        Assertions.assertTrue(response == goodResponse);

        response = service.fetchStory(badRequest);
        Assertions.assertTrue(response == badResponse);
    }
}
