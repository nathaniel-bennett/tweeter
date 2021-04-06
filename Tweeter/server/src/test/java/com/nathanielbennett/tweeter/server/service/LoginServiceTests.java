package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
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

public class LoginServiceTests {

    /*

    LoginServiceImpl service;
    LoginDAO dao;
    LoginRequest goodRequest;
    LoginResponse goodResponse;
    LoginRequest badRequest;
    LoginResponse badResponse;

    @BeforeEach
    public void setup() {

        dao = Mockito.mock(LoginDAO.class);
        service = Mockito.spy(LoginServiceImpl.class);

        goodRequest = new LoginRequest("HELLO","FollowME");
        goodResponse = new LoginResponse(null, null);
        Mockito.when(dao.login(goodRequest)).thenReturn(goodResponse);

        badRequest = new LoginRequest("HELLO", "FollowME");
        badResponse = new LoginResponse("failure");
        Mockito.when(dao.login(badRequest)).thenReturn(badResponse);

        Mockito.when(service.getLoginDao()).thenReturn(dao);

    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new LoginRequest(null,"@me")));
        args.add(Arguments.of(new LoginRequest("","@me")));
        args.add(Arguments.of(new LoginRequest("Hello",null)));
        args.add(Arguments.of(new LoginRequest("@me","")));

        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput")
    public void testBadInputThrowsException(LoginRequest request) {
        Assertions.assertThrows(BadRequestException.class, () -> {
            service.login(request);
        });
    }

    @Test
    public void testServiceReturnsCorrectObject() {
        LoginResponse response = service.login(goodRequest);
        Assertions.assertTrue(response == goodResponse);

        response = service.login(badRequest);
        Assertions.assertTrue(response == badResponse);
    }
    */
}
