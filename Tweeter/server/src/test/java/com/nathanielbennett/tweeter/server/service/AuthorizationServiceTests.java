package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.AuthorizationService;
import com.nathanielbennett.tweeter.model.service.request.AuthorizationRequest;
import com.nathanielbennett.tweeter.model.service.response.AuthorizationResponse;
import com.nathanielbennett.tweeter.server.dao.AuthTokenDAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AuthorizationServiceTests {
    AuthorizationRequest goodRequest;
    AuthorizationRequest badRequest;
    AuthTokenDAO dao;
    AuthorizationServiceImpl service;

    @BeforeEach
    public void setup() {
        dao = Mockito.mock(AuthTokenDAO.class);
        service = Mockito.spy(AuthorizationServiceImpl.class);

        Mockito.when(service.getAuthTokenDAO()).thenReturn(dao);

        goodRequest = new AuthorizationRequest("GoodUser", new AuthToken());

        Mockito.when(dao.checkToken(goodRequest.getAuthToken(), goodRequest.getUsername())).thenReturn(true);

        badRequest = new AuthorizationRequest("BadUser", new AuthToken());

        Mockito.when(dao.checkToken(badRequest.getAuthToken(), badRequest.getUsername())).thenReturn(false);

    }

    @Test
    @DisplayName("Verify that a valid request returns a good response")
    public void testValidInput() {
        AuthorizationResponse response = service.isAuthorized(goodRequest);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    @DisplayName("Verify that an invalid request returns a bad response")
    public void testInvalidInput() {
        AuthorizationResponse response = service.isAuthorized(badRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertEquals("Invalid or expired auth token.", response.getErrorMessage());
    }

}
