package com.nathanielbennett.tweeter.model.net;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import java.util.List;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    private DummyServer dummyServer;


    public ServerFacade() {
        dummyServer = DummyServer.getInstance();
    }

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        return dummyServer.login(request);
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowResponse getFollowees(FollowRequest request) {
        return dummyServer.getFollowees(request);
    }

    /**
     * Returns the users the the user specified in the request is being followed by. Uses information
     * in the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation returns
     * generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers response
     */
    public FollowResponse getFollowers(FollowRequest request) {
        return dummyServer.getFollowers(request);
    }

    /**
     * Returns the status that the user specified in the request. Uses information in the request object
     * to limit the number of statuses returned and to return the next set of statuses after any
     * that were returned in the previous request. The current implementation returns generated data
     * and doesn't actually make a network request.
     *
     * @param request contains information about the user whos status are to be returned
     * @return the story response.
     */
    public StatusResponse getStory(StatusRequest request) {
        return dummyServer.getStory(request);
    }

    public StatusResponse getFeed(StatusRequest request) {
        return dummyServer.getFeed(request);
    }

    /**
     * Returns the list of dummy status for the feed. This is written as a separate method to allow
     * mocking of the feed.
     *
     * @return the dummy feed.
     */
    List<Status> getDummyFeed() {
        return dummyServer.getDummyFeed();
    }

    /**
     * Returns the list of dummy status for the story. This is written as a separate method to allow
     * mocking of the story.
     *
     * @return the dummy story.
     */
    List<Status> getDummyStory() {
        return dummyServer.getDummyStory();
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return dummyServer.getDummyFollowees();
    }

    /**
     * Returns the list of dummy follower data. This is written as a separate method to allow
     * mocking of the followers.
     *
     * @return the followers.
     */
    List<User> getDummyFollowers() {
        return dummyServer.getDummyFollowers();
    }

    /**
     * Logs out a user with an AuthToken
     * @param logoutRequest
     * @return
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        return dummyServer.logout(logoutRequest);
    }
}
