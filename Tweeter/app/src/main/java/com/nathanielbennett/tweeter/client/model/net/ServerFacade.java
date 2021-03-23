package com.nathanielbennett.tweeter.client.model.net;

import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.CheckFollowingStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.FeedStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.FollowUserStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.FolloweeStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.FollowerStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.LoginStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.LogoutStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.PostStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.RegisterStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.StoryStrategy;
import com.nathanielbennett.tweeter.client.model.net.webrequeststrategies.UnfollowUserStrategy;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

import java.io.IOException;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    /*
     ***********************************************************************************************
     *                                    SERVER FACADE API
     ***********************************************************************************************
     */


    /**
     * Performs a user registration and if successful, returns the registered user and an auth
     * token. The current implementation is hard-coded to return a dummy user and doesn't actually
     * make a network request.
     *
     * @param request contains all information needed to register a user.
     * @return the register response.
     */
    public RegisterResponse register(RegisterRequest request) throws IOException {
        ClientCommunicator registerCommunicator = new ClientCommunicator(new RegisterStrategy());

        return (RegisterResponse) registerCommunicator.doWebRequest(request, null);
    }


    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) throws IOException {
        ClientCommunicator loginCommunicator = new ClientCommunicator(new LoginStrategy());

        return (LoginResponse) loginCommunicator.doWebRequest(request, null);
    }


    /**
     * Posts to the server a request to follow a given user and, if successful, returns a message
     * indicating so.
     * @param request contains information needed to follow a particular user
     * @return The response from the server as to whether the user was successfully followed or not.
     */
    public FollowUserResponse follow(FollowUserRequest request) throws IOException {
        ClientCommunicator followCommunicator = new ClientCommunicator(new FollowUserStrategy());

        return (FollowUserResponse) followCommunicator.doWebRequest(request, request.getAuthToken());
    }

    /**
     * Posts to the server a request to unfollow a given user and, if successful, returns a message
     * indicating so.
     * @param request contains information needed to unfollow a particular user
     * @return The response from the server as to whether the user was successfully unfollowed or not.
     */
    public UnfollowUserResponse unfollow(UnfollowUserRequest request) throws IOException {
        ClientCommunicator unfollowCommunicator = new ClientCommunicator(new UnfollowUserStrategy());

        return (UnfollowUserResponse) unfollowCommunicator.doWebRequest(request, request.getAuthToken());
    }

    /**
     * Posts to the server a query to know whether a given user is being followed or not and,
     * if successful, returns a message indicating whether the user is being followed or not..
     * @param request contains information needed to check the current state of following for a user.
     * @return The response from the server as to whether the user is followed or not.
     */
    public CheckFollowingResponse isFollowing(CheckFollowingRequest request) throws IOException {
        ClientCommunicator communicator = new ClientCommunicator(new CheckFollowingStrategy());

        return (CheckFollowingResponse) communicator.doWebRequest(request, request.getAuthToken());
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
    public FollowResponse getFollowing(FollowRequest request) throws IOException {
        ClientCommunicator communicator = new ClientCommunicator(new FolloweeStrategy());

        return (FollowResponse) communicator.doWebRequest(request, null);
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
    public FollowResponse getFollowers(FollowRequest request) throws IOException {
        ClientCommunicator communicator = new ClientCommunicator(new FollowerStrategy());

        return (FollowResponse) communicator.doWebRequest(request, null);
    }


    public PostResponse addToStory(PostRequest request) throws IOException {
        ClientCommunicator communicator = new ClientCommunicator(new PostStrategy());

        return (PostResponse) communicator.doWebRequest(request, request.getAuthToken());
    }

    /**
     * Returns the status that the user specified in the request. Uses information in the request object
     * to limit the number of statuses returned and to return the next set of statuses after any
     * that were returned in the previous request. The current implementation returns generated data
     * and doesn't actually make a network request.
     *
     * @param request contains information about the user whose status are to be returned
     * @return the story response.
     */
    public StatusResponse getStory(StatusRequest request) throws IOException {
        ClientCommunicator communicator = new ClientCommunicator(new StoryStrategy());

        return (StatusResponse) communicator.doWebRequest(request, null);
    }

    public StatusResponse getFeed(StatusRequest request) throws IOException {
        ClientCommunicator communicator = new ClientCommunicator(new FeedStrategy());

        return (StatusResponse) communicator.doWebRequest(request, null);
    }


    /**
     * Logs out the user specified in the request. The current implementation returns
     * generated data and doesn't actually make a network request.
     *
     * @param logoutRequest Contains information about the user to be logged out.
     * @return A response indicating whether the logout was successful or not.
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        ClientCommunicator communicator = new ClientCommunicator(new LogoutStrategy());

        return (LogoutResponse) communicator.doWebRequest(logoutRequest, logoutRequest.getAuthToken());
    }
}