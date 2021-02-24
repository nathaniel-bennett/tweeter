package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class LoginPresenterTest {


    private LoginRequest request;
    private LoginResponse response;
    private LoginService mockLoginService;
    private LoginPresenter presenter;
    private LoginRequest missingUsernameRequest;
    private LoginResponse missingUsernameResponse;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        AuthToken testToken = new AuthToken();
        byte[] image = {0x01, 0x01, 0x01, 0x01};
        String password = "testPassword1";

        request = new LoginRequest(currentUser.getAlias(), password);
        response = new LoginResponse(currentUser, testToken);

        missingUsernameRequest = new LoginRequest(null, password);
        missingUsernameResponse = new LoginResponse("Username missing");

        // Create a mock FollowingService
        mockLoginService = Mockito.mock(LoginService.class);
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        Mockito.when(mockLoginService.login(missingUsernameRequest)).thenReturn(missingUsernameResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.login(request));
    }

    @Test
    public void testGetFollowing_returnBadResult() throws IOException {
        Assertions.assertEquals(missingUsernameResponse, presenter.login(missingUsernameRequest));
    }
}