package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.FollowServiceProxy;
import com.nathanielbennett.tweeter.client.model.service.LoginServiceProxy;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FollowServiceTest {

    private FollowService followService;
    private FollowUserRequest validFollowRequest;
    private FollowUserRequest invalidFollowRequest;
    private UnfollowUserRequest validUnFollowRequest;
    private UnfollowUserRequest invalidUnFollowRequest;
    private CheckFollowingRequest validCheckFollowRequest;
    private CheckFollowingRequest invalidCheckFollowRequest;
    private AuthToken token;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        followService = new FollowServiceProxy();

        if (token == null) {
            LoginService service = new LoginServiceProxy();
            LoginResponse response = service.login(new LoginRequest("testUser", "testUser"));
            token = response.getAuthToken();
        }

        // follow requests
        validFollowRequest = new FollowUserRequest("testUser", token, "guy19");
        invalidFollowRequest = new FollowUserRequest("@Dumb", token, "@ME");

        // unfollow requets
        validUnFollowRequest = new UnfollowUserRequest("testUser", token, "guy19");
        invalidUnFollowRequest = new UnfollowUserRequest("@AmyAmes", token, "@BonnieBeatty");

        // check following requests
        validCheckFollowRequest = new CheckFollowingRequest("testUser", token, "dummyUser");
        invalidCheckFollowRequest = new CheckFollowingRequest("dummyUser", token, "@ME");

    }

    @Test
    public void validFollowRequest() throws IOException {

        FollowUserResponse response = followService.follow(validFollowRequest);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void invalidFollowRequest () throws IOException {

        FollowUserResponse response = followService.follow(invalidFollowRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }

    @Test
    public void validUnFollowRequest() throws IOException {

        UnfollowUserResponse response = followService.unfollow(validUnFollowRequest);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void invalidUnFollowRequest () throws IOException {

        UnfollowUserResponse response = followService.unfollow(invalidUnFollowRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }

    @Test
    public void validCheckFollowRequest() throws IOException {

        CheckFollowingResponse response = followService.checkFollowStatus(validCheckFollowRequest);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void invalidCheckFollowRequest () throws IOException {

        CheckFollowingResponse response = followService.checkFollowStatus(invalidCheckFollowRequest);
        Assertions.assertFalse(response.getSuccess());
        Assertions.assertNotNull(response.getErrorMessage());
    }
}
