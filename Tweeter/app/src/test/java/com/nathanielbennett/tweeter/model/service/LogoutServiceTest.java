package com.nathanielbennett.tweeter.model.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class LogoutServiceTest {

    private LogoutRequest nullUsername;
    private LogoutRequest nullAuthToken;
    private LogoutRequest badRequest;
    private LogoutRequest goodRequest;

    private LogoutResponse badResponse;
    private LogoutResponse goodResponse;

    private LogoutService logoutServiceSpy;
    private ServerFacade mockServerFacade;

    @BeforeEach
    public void setup() {

        nullUsername = new LogoutRequest(null, new AuthToken());
        nullAuthToken = new LogoutRequest("@Chuck", null);
        badRequest = new LogoutRequest("@BadGuy", new AuthToken());
        goodRequest = new LogoutRequest("@GoodGuy", new AuthToken());

        badResponse = new LogoutResponse("Error, user not found");
        goodResponse = new LogoutResponse();

        mockServerFacade = Mockito.mock(ServerFacade.class);
        logoutServiceSpy = Mockito.spy(new LogoutService());

        Mockito.when(mockServerFacade.logout(badRequest)).thenReturn(badResponse);
        Mockito.when(mockServerFacade.logout(goodRequest)).thenReturn(goodResponse);
        Mockito.when(logoutServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     *  Verifies that when a null request is passed into {@link LogoutService#logout(LogoutRequest)}
     *  a Null pointer exception is thrown.
     */
    @Test
    public void testLogout_nullRequest_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
           logoutServiceSpy.logout(null);
        });
    }

    /**
     *  Verifies that when a request with a null userName is passed into {@link LogoutService#logout(LogoutRequest)}
     *  a Null pointer exception is thrown.
     */
    @Test
    public void testLogout_nullUsername_throwsException() throws IOException {
        Assertions.assertEquals("Username required to log out of session",
                logoutServiceSpy.logout(nullUsername).getMessage());
    }

    /**
     *  Verifies that when a request with a null authToken is passed into {@link LogoutService#logout(LogoutRequest)}
     *  a Null pointer exception is thrown.
     */
    @Test
    public void testLogout_nullAuthToken_throwsException() throws IOException {
        Assertions.assertEquals("Auth token required to log out of session",
                logoutServiceSpy.logout(nullAuthToken).getMessage());
    }

    /**
     *  Verifies that when a bad request is passed into {@link LogoutService#logout(LogoutRequest)}
     *  the correct response is sent.
     */
    @Test
    public void testLogout_badRequest_correctResponse() throws IOException {
        Assertions.assertEquals(badResponse, logoutServiceSpy.logout(badRequest));
    }

    /**
     *  Verifies that when a good request is passed into {@link LogoutService#logout(LogoutRequest)}
     *  a Null pointer exception is thrown.
     */
    @Test
    public void testLogout_goodRequest_correctResponse() throws IOException {
        Assertions.assertEquals(goodResponse, logoutServiceSpy.logout(goodRequest));
    }


}
