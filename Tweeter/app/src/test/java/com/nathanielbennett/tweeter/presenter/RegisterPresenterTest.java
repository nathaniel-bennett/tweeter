package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.RegisterService;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class RegisterPresenterTest {

    private RegisterRequest request;
    private RegisterResponse response;
    private RegisterService mockRegisterService;
    private RegisterPresenter presenter;
    private RegisterRequest missingUsernameRequest;
    private RegisterResponse missingUsernameResponse;

    @BeforeEach
    public void setup() throws IOException {

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        AuthToken testToken = new AuthToken();
        byte[] image = {0x01, 0x01, 0x01, 0x01};

        request = new RegisterRequest(resultUser1.getFirstName(), resultUser1.getLastName(), resultUser1.getAlias(), "testPassword1", image);
        response = new RegisterResponse(resultUser1, testToken);

        missingUsernameRequest = new RegisterRequest(resultUser1.getFirstName(), resultUser1.getLastName(), "", "testPassword2", image);
        missingUsernameResponse = new RegisterResponse("Username missing");

        // Create a mock FollowingService
        mockRegisterService = Mockito.mock(RegisterService.class);
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        Mockito.when(mockRegisterService.register(missingUsernameRequest)).thenReturn(missingUsernameResponse);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterService);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.register(request));
    }

    @Test
    public void testGetFollowing_returnBadResult() throws IOException {
        Assertions.assertEquals(missingUsernameResponse, presenter.register(missingUsernameRequest));
    }


}