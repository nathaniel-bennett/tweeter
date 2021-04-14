package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.WeakPasswordException;
import com.nathanielbennett.tweeter.server.model.StoredUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RegisterServiceTests {

    RegisterServiceImpl service;
    UserDAO dao;
    AuthTokenDAO authDAO;
    RegisterRequest goodRequest;

    @BeforeEach
    public void setup() {

        dao = Mockito.mock(UserDAO.class);
        authDAO = Mockito.mock(AuthTokenDAO.class);
        service = Mockito.spy(RegisterServiceImpl.class);

        Mockito.when(service.getAuthTokenDAO()).thenReturn(authDAO);
        Mockito.when(service.getUserDAO()).thenReturn(dao);

        goodRequest = new RegisterRequest("Hank", "Dog", "@HankD", "@hello000", "hi".getBytes());
        StoredUser user = new StoredUser(goodRequest.getFirstName(), goodRequest.getLastName(), "hash", goodRequest.getUsername(), "nowhere", 0, 0);
        Mockito.when(dao.getUser(goodRequest.getUsername())).thenReturn(null);
        Mockito.when(service.requestToStoredUser(goodRequest)).thenReturn(user);
        Mockito.when(authDAO.createToken(goodRequest.getUsername())).thenReturn(new AuthToken());
    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new RegisterRequest(null, "Dog", "@HankD", "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("", "Dog", "@HankD", "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", null, "@HankD", "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", "", "@HankD", "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", "Dog", "@HankD", null, "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", "Dog", "@HankD", "", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", "Dog", "@HankD", "@hello", null)));
        args.add(Arguments.of(new RegisterRequest("Hank", "Dog", "@HankD", "@hello", "".getBytes())));

        return args.stream();
    }

    @ParameterizedTest
    @DisplayName("Test that failures should work")
    @MethodSource("generateInvalidTestInput")
    public void testBadInputThrowsException(RegisterRequest request) {

        if (request.getPassword() == null || request.getPassword().isEmpty() || request.getUsername().length() < 8) {
            Assertions.assertThrows(WeakPasswordException.class, () -> {
                service.register(request);
            });
        } else if (request.getUsername() == null || request.getUsername().isEmpty()) {
            Assertions.assertThrows(BadRequestException.class, () -> {
                service.register(request);
            });
        } else {
            Assertions.assertThrows(BadRequestException.class, () -> {
                service.register(request);
            });
        }

    }

    @Test
    public void testServiceReturnsCorrectObject() {
        RegisterResponse response = service.register(goodRequest);
        Assertions.assertTrue(response.getSuccess());
    }
}
