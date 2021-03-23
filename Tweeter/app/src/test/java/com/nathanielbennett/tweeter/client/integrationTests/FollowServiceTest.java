package com.nathanielbennett.tweeter.client.integrationTests;

import com.nathanielbennett.tweeter.client.model.service.FollowServiceProxy;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.service.FollowService;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

import org.junit.jupiter.api.Assertions;
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

    @BeforeEach
    public void setup() {
        followService = new FollowServiceProxy();

        // follow requests
        validFollowRequest = new FollowUserRequest("@AmyAmes", new AuthToken(), "@BonnieBeatty");
        invalidFollowRequest = new FollowUserRequest("@dAmyAmes", new AuthToken(), "dummyUser");

        // unfollow requets
        validUnFollowRequest = new UnfollowUserRequest("dummyUser", new AuthToken(), "@BobBobson");
        invalidUnFollowRequest = new UnfollowUserRequest("@AmyAmes", new AuthToken(), "@BonnieBeatty");

        // check following requests
        validCheckFollowRequest = new CheckFollowingRequest("dummyUser", new AuthToken(), "@AmyAmes");
        invalidCheckFollowRequest = new CheckFollowingRequest("dummyUser", new AuthToken(), "@ME");

    }

    @Test
    public void validFollowRequest() throws IOException {

        FollowUserResponse response = followService.follow(validFollowRequest);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void invalidFollowRequest () throws IOException {

        FollowUserResponse response = followService.follow(validFollowRequest);
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
