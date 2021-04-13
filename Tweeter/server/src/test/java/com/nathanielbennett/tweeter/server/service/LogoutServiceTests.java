package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
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

public class LogoutServiceTests {


    LogoutServiceImpl service;
    AuthTokenDAO dao;
    LogoutRequest goodRequest;

    @BeforeEach
    public void setup() {

        dao = Mockito.mock(AuthTokenDAO.class);
        service = Mockito.spy(LogoutServiceImpl.class);

        goodRequest = new LogoutRequest("HELLO", new AuthToken());

        Mockito.when(service.getAuthTokenDAO()).thenReturn(dao);

    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new LogoutRequest(null,new AuthToken())));
        args.add(Arguments.of(new LogoutRequest("",new AuthToken())));

        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput")
    public void testBadInputThrowsException(LogoutRequest request) {
        Assertions.assertThrows(BadRequestException.class, () -> {
            service.logout(request);
        });
    }

    @Test
    @DisplayName("Test that invalid authToken throws exception")
    public void testBadAuthToken() {
        Assertions.assertThrows(NotAuthorizedException.class, () -> {
            service.logout(new LogoutRequest("null", null));
        });
    }

    @Test
    public void testServiceReturnsCorrectObject() {
        LogoutResponse response = service.logout(goodRequest);
        Assertions.assertTrue(response.getSuccess());
    }
}
