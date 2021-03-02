package com.nathanielbennett.tweeter.client.presenter;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.model.service.LogoutService;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class MainPresenterTest {

    private LogoutRequest request;
    private LogoutResponse response;
    private LogoutService mockLogoutService;
    private MainPresenter presenter;
    private LogoutRequest missingUsernameRequest;
    private LogoutResponse missingUsernameResponse;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        AuthToken testToken = new AuthToken();
        byte[] image = {0x01, 0x01, 0x01, 0x01};
        String password = "testPassword1";

        request = new LogoutRequest(currentUser.getAlias(), testToken);
        response = new LogoutResponse();

        missingUsernameRequest = new LogoutRequest(null, testToken);
        missingUsernameResponse = new LogoutResponse("Username missing");

        // Create a mock FollowingService
        mockLogoutService = Mockito.mock(LogoutService.class);
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);

        Mockito.when(mockLogoutService.logout(missingUsernameRequest)).thenReturn(missingUsernameResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new MainPresenter(new MainPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
    }

    @Test
    public void testLogout_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.logout(request));
    }

    @Test
    public void testLogout_returnBadResult() throws IOException {
        Assertions.assertEquals(missingUsernameResponse, presenter.logout(missingUsernameRequest));
    }
}
