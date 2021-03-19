package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.server.dao.RegisterDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.WeakPasswordException;

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

public class RegisterServiceTests {

    RegisterServiceImpl service;
    RegisterDAO dao;
    RegisterRequest goodRequest;
    RegisterResponse goodResponse;
    RegisterRequest badRequest;
    RegisterResponse badResponse;

    @BeforeEach
    public void setup() {

        dao = Mockito.mock(RegisterDAO.class);
        service = Mockito.spy(RegisterServiceImpl.class);

        goodRequest = new RegisterRequest("Hank", "Dog", "@HankD", "@hello", "hi".getBytes());
        goodResponse = new RegisterResponse("ok");
        Mockito.when(dao.register(goodRequest)).thenReturn(goodResponse);

        badRequest = new RegisterRequest("Hank", "Dog", "@HankD", "@hello", "BAD".getBytes());
        badResponse = new RegisterResponse("failure");
        Mockito.when(dao.register(badRequest)).thenReturn(badResponse);

        Mockito.when(service.getRegisterDAO()).thenReturn(dao);

    }

    static Stream<Arguments> generateInvalidTestInput() {
        List<Arguments> args = new ArrayList<>();

        args.add(Arguments.of(new RegisterRequest(null, "Dog", "@HankD", "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("", "Dog", "@HankD", "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", null, "@HankD", "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", "", "@HankD", "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", "Dog", null, "@hello", "GOOD".getBytes())));
        args.add(Arguments.of(new RegisterRequest("Hank", "Dog", "", "@hello", "GOOD".getBytes())));
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

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            Assertions.assertThrows(WeakPasswordException.class, () -> {
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
        Assertions.assertTrue(response == goodResponse);

        response = service.register(badRequest);
        Assertions.assertTrue(response == badResponse);
    }
}
