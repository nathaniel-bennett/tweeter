package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
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
import java.util.List;
import java.util.stream.Stream;

public class LoginServiceTests {

    LoginServiceImpl service;
    UserDAO dao;
    AuthTokenDAO authTokenDAO;
    LoginRequest goodRequest;
    LoginResponse goodResponse;

    @BeforeEach
    public void setup() {

        dao = Mockito.mock(UserDAO.class);
        authTokenDAO = Mockito.mock(AuthTokenDAO.class);
        service = Mockito.spy(LoginServiceImpl.class);

        goodRequest = new LoginRequest("HELLO","FollowME");
        goodResponse = new LoginResponse(null, null);

        //Mockito.when(dao.login(goodRequest)).thenReturn(goodResponse);
        Mockito.when(dao.getUser(goodRequest.getUsername())).thenReturn(new StoredUser("hello", "me", "$2a$10$w4Yhe34QPsYgpEwXuXbJ3uNHhQGAImI1z8RsRyVe91t..pO0eVxq.", "@me", "", 0, 0));
        Mockito.when(authTokenDAO.createToken(goodRequest.getUsername())).thenReturn(new AuthToken());
                Mockito.when(service.getUserDAO()).thenReturn(dao);
        Mockito.when(service.getAuthTokenDAO()).thenReturn(authTokenDAO);

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
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getAuthToken());
    }

}
