package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.FollowService;
import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class UserPresenterTest {

    private FollowUserRequest followRequest;
    private FollowUserResponse followResponse;
    private UnfollowUserRequest unfollowRequest;
    private UnfollowUserResponse unfollowResponse;
    private CheckFollowingRequest checkFollowingRequest;
    private CheckFollowingResponse checkFollowingResponse;
    private FollowService mockFollowService;
    private UserPresenter presenter;
    private FollowUserRequest followRequestBad;
    private FollowUserResponse followResponseBad;
    private UnfollowUserRequest unfollowRequestBad;
    private UnfollowUserResponse unfollowResponseBad;
    private CheckFollowingRequest checkFollowingRequestBad;
    private CheckFollowingResponse checkFollowingResponseBad;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);
        User otherUser = new User("First", "Last", null);

        AuthToken testToken = new AuthToken();
        byte[] image = {0x01, 0x01, 0x01, 0x01};
        String password = "testPassword1";

        followRequest = new FollowUserRequest(currentUser.getAlias(), testToken, otherUser.getAlias());
        followResponse = new FollowUserResponse();

        unfollowRequest = new UnfollowUserRequest(currentUser.getAlias(), testToken, otherUser.getAlias());
        unfollowResponse = new UnfollowUserResponse();

        checkFollowingRequest = new CheckFollowingRequest(currentUser.getAlias(), testToken, otherUser.getAlias());
        checkFollowingResponse = new CheckFollowingResponse(false);


        followRequestBad = new FollowUserRequest(null, testToken, otherUser.getAlias());
        followResponseBad = new FollowUserResponse("Username missing");

        unfollowRequestBad = new UnfollowUserRequest(null, testToken, otherUser.getAlias());
        unfollowResponseBad = new UnfollowUserResponse("Username missing");

        checkFollowingRequestBad = new CheckFollowingRequest(null, testToken, otherUser.getAlias());
        checkFollowingResponseBad = new CheckFollowingResponse("Username missing");

        // Create a mock FollowingService
        mockFollowService = Mockito.mock(FollowService.class);
        Mockito.when(mockFollowService.follow(followRequest)).thenReturn(followResponse);
        Mockito.when(mockFollowService.unfollow(unfollowRequest)).thenReturn(unfollowResponse);
        Mockito.when(mockFollowService.checkFollowStatus(checkFollowingRequest)).thenReturn(checkFollowingResponse);

        Mockito.when(mockFollowService.follow(followRequestBad)).thenReturn(followResponseBad);
        Mockito.when(mockFollowService.unfollow(unfollowRequestBad)).thenReturn(unfollowResponseBad);
        Mockito.when(mockFollowService.checkFollowStatus(checkFollowingRequestBad)).thenReturn(checkFollowingResponseBad);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new UserPresenter(new UserPresenter.View() {
        }));
        Mockito.when(presenter.getFollowService()).thenReturn(mockFollowService);
    }

    @Test
    public void testFollow_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(followResponse, presenter.followUser(followRequest));
    }

    @Test
    public void testFollow_returnBadResult() throws IOException {
        Assertions.assertEquals(followResponseBad, presenter.followUser(followRequestBad));
    }

    @Test
    public void testUnfollow_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(unfollowResponse, presenter.unfollowUser(unfollowRequest));
    }

    @Test
    public void testUnfollow_returnBadResult() throws IOException {
        Assertions.assertEquals(unfollowResponseBad, presenter.unfollowUser(unfollowRequestBad));
    }


    @Test
    public void testCheckFollow_returnsServiceResult() throws IOException {

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(checkFollowingResponse, presenter.checkFollowing(checkFollowingRequest));
    }

    @Test
    public void testCheckFollow_returnBadResult() throws IOException {
        Assertions.assertEquals(checkFollowingResponseBad, presenter.checkFollowing(checkFollowingRequestBad));
    }
}