package com.nathanielbennett.tweeter.model.net;

import android.util.Log;

import com.nathanielbennett.tweeter.BuildConfig;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    private static final AuthToken authToken1 = new AuthToken();
    private static final DataCache dc = DataCache.getInstance();

    /*
     ***********************************************************************************************
     *                                    SERVER FACADE API
     ***********************************************************************************************
     */


    /**
     * Performs a user registration and if successful, returns the registered user and an auth
     * token. The current implementation is hard-coded to return a dummy user and doesn't actually
     * make a network request.
     * @param request contains all information needed to register a user.
     * @return the register response.
     */
    public RegisterResponse register(RegisterRequest request) {
        if (request.getUsername().equals("dummyUserName")) {
            return new RegisterResponse("Username taken; please try another username");
        } else {
            User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(),
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            return new RegisterResponse(user, authToken1);
        }
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
        if (request.getPassword().equals("dummyUser") && request.getUsername().equals("dummyUser")){
//            User user = new User("Test", "User", "helloWorld", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            User user = dc.getUser("dummyUser");

            return new LoginResponse(user, authToken1);
        }
        else{
            return new LoginResponse("Failed to authenticate user on login");
        }
    }



    /**
     * TODO: document lol
     * @param request
     * @return
     */
    public FollowUserResponse follow(FollowUserRequest request) {
        return new FollowUserResponse();
    }


    public UnfollowUserResponse unfollow(UnfollowUserRequest request) {
        return new UnfollowUserResponse();
    }



    public CheckFollowingResponse isFollowing(CheckFollowingRequest request) {
        // TODO: implement this

        return new CheckFollowingResponse(false);
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
    public FollowResponse getFollowing(FollowRequest request) {

        // Used in place of assert statements because Android does not support them
        if (BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowing = getFollowingFromUserName(request.getFollowAlias());
        List<User> responseFollowing = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFollowStartingIndex(request.getLastFollowAlias(), allFollowing);

            for(int limitCounter = 0; followeesIndex < allFollowing.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowing.add(allFollowing.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowing.size();
        }

        return new FollowResponse(responseFollowing, hasMorePages);
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
        if (BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getFollowAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getFollowersByUserName(request.getFollowAlias());
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followersIndex = getFollowStartingIndex(request.getLastFollowAlias(), allFollowers);

            for (int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowResponse(responseFollowers, hasMorePages);
    }

    public PostResponse addToStory(PostRequest request) {
        if (BuildConfig.DEBUG) {
            if (request.getUser() == null) {
                throw new AssertionError("No user object provided");
            }
        }

        return new PostResponse(true, "Status added successfully!");
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
    public StatusResponse getStory(StatusRequest request) {
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUserToGet() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getStory(request.getUserToGet());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int storyIndex = getStatusStartingIndex(request.getLastStatusSent(), allStatuses);

            for (int limitCounter = 0; storyIndex < allStatuses.size() && limitCounter < request.getLimit(); storyIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(storyIndex));
            }

            hasMorePages = storyIndex < allStatuses.size();
        }

        return new StatusResponse(hasMorePages, responseStatuses);
    }

    public StatusResponse getFeed(StatusRequest request) {
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUserToGet() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getFeedFromDC(request.getUserToGet());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int storyIndex = getStatusStartingIndex(request.getLastStatusSent(), allStatuses);

            for (int limitCounter = 0; storyIndex < allStatuses.size() && limitCounter < request.getLimit(); storyIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(storyIndex));
            }

            hasMorePages = storyIndex < allStatuses.size();
        }

        return new StatusResponse(hasMorePages, responseStatuses);
    }







    /**
     * Logs out the user specified in the request. The current implementation returns
     * generated data and doesn't actually make a network request.
     * @param logoutRequest Contains information about the user to be logged out.
     * @return A response indicating whether the logout was successful or not.
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) {

        Log.i("Received token: ", logoutRequest.getAuthToken().getTokenID());
        Log.i("Checked token: ", authToken1.getTokenID());

        if (!logoutRequest.getAuthToken().equals(authToken1)) {
            return new LogoutResponse("Invalid AuthToken");
        } else {
            return new LogoutResponse();
        }
    }


    /*
     ***********************************************************************************************
     *                                   HELPER FUNCTIONS
     ***********************************************************************************************
     */



    /**
     * Determines the index for the first status in the specified 'allStatuses' list that should be
     * returned in the current request. This will be the index of the next status after the specified
     * 'message'.
     *
     * @param message the last message seen.
     * @param allStatuses all of the statuses.
     * @return the index of the first status to be returned.
     */
    private int getStatusStartingIndex(String message, List<Status> allStatuses) {
        int statusIndex = 0;

        if (message != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(message.equals(allStatuses.get(i).getStatusMessage())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return;
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }

    /**
     * Determines the index for the first followUser in the specified 'allFollow' list that should
     * be returned in the current request. This will be the index of the next followUser after the
     * specified 'lastFollow'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollow the generated list of followUser from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFollowStartingIndex(String lastFolloweeAlias, List<User> allFollow) {

        int followeesIndex = 0;

        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollow.size(); i++) {
                if(lastFolloweeAlias.equals(allFollow.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns the list of dummy status for the feed. This is written as a separate method to allow
     * mocking of the feed.
     *
     * @return the dummy feed.
     */
    List<Status> getFeedFromDC(User user) {
        ArrayList<User> followers = dc.getFollowing(user);
        ArrayList<Status> feed = new ArrayList<>();
        for (int i = 0; i < followers.size(); i++){
            ArrayList<Status> statuses = dc.getStatuses(user);
            feed.addAll(statuses);
        }
//        Collections.sort(feed, new Comparator<Status>() {
//            @Override
//            public int compare(Status o1, Status o2) {
//                String date1 = o1.getDatePosted();
//                String date2 = o2.getDatePosted();
//                Date d1 =
//                if (o1.getDatePosted()
//            }
//        });
        return feed;
    }

    /**
     * Returns the list of dummy status for the story. This is written as a separate method to allow
     * mocking of the story.
     *
     * @return the dummy story.
     */
    List<Status> getStory(User user) {
        return dc.getStatuses(user);
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getFollowingFromUserName(String username) {
        User user = dc.getUser(username);
        return dc.getFollowing(user);
    }

    /**
     * Returns the list of dummy follower data. This is written as a separate method to allow
     * mocking of the followers.
     *
     * @return the followers.
     */
    List<User> getFollowersByUserName(String username) {
        User user = dc.getUser(username);
        return dc.getFollowers(user);
    }
}