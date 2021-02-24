package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class LoginServiceTest {

    private LoginRequest nullUsername;
    private LoginRequest nullPassword;
    private LoginRequest badRequest;
    private LoginRequest goodRequest;

    private LoginResponse badResponse;
    private LoginResponse goodResponse;

    private LoginService loginServiceSpy;
    private ServerFacade mockServerFacade;

    @BeforeEach
    public void setup() {

        nullUsername = new LoginRequest(null, "password");
        nullPassword = new LoginRequest("Void", null);
        badRequest = new LoginRequest("Bad", "password");
        goodRequest = new LoginRequest("Good", "password");

        badResponse = new LoginResponse("Bad User");
        badResponse = new LoginResponse("Good user");

        mockServerFacade = Mockito.mock(ServerFacade.class);
        loginServiceSpy = Mockito.spy(new LoginService());

        Mockito.when(mockServerFacade.login(badRequest)).thenReturn(badResponse);
        Mockito.when(mockServerFacade.login(goodRequest)).thenReturn(goodResponse);
        Mockito.when(loginServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verifies that when a null request is passed into {@link LoginService#login(LoginRequest)}
     * a Null Pointer Exception is thrown.
     */
    @Test
    public void testLogin_nullRequest_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            loginServiceSpy.login(null);
        });
    }

    /**
     * Verifies that when a request with a null username is passed into {@link LoginService#login(LoginRequest)}
     * a correct response is returned.
     */
    @Test
    public void testLogin_nullUsername_correctResponse() throws IOException {
        Assertions.assertEquals("A Username is required to sign in (please enter)",
                loginServiceSpy.login(nullUsername).getMessage());
    }

    /**
     * Verifies that when a request with a null password is passed into {@link LoginService#login(LoginRequest)}
     * the correct response is returned.
     */
    @Test
    public void testLogin_nullPassword_correctResponse() throws IOException {
        Assertions.assertEquals("A password is required to sign in (please enter)",
                loginServiceSpy.login(nullPassword).getMessage());
    }

    /**
     * Verifies that when a bad request is passed into {@link LoginService#login(LoginRequest)}
     * the correct response is returned.
     */
    @Test
    public void testLogin_badRequest_correctResponse() throws IOException {
        Assertions.assertEquals(badResponse, loginServiceSpy.login(badRequest));
    }

    /**
     * Verifies that when a bad request is passed into {@link LoginService#login(LoginRequest)}
     * the correct response is returned.
     */
    @Test
    public void testLogin_goodRequest_correctResponse() throws IOException {
        Assertions.assertEquals(goodResponse, loginServiceSpy.login(goodRequest));
    }




}
