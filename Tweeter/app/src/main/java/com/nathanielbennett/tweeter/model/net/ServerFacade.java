package com.nathanielbennett.tweeter.model.net;

import com.nathanielbennett.tweeter.BuildConfig;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.request.StoryRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.model.service.response.StoryResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    // This is the hard coded followee data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    private final User dummyUser = new User("Dummy", "User", "dummyUser", MALE_IMAGE_URL);
    private final Status dummyUserStatus1 = new Status(dummyUser, "Hello Status!", "Feburary 17 2021 9:16 PM", new ArrayList<User>());
    private final Status dummyUserStatus2 = new Status(dummyUser, "Goodbye Status!", "Feburary 17 2021 9:17 PM", new ArrayList<User>());
    private final Status dummyUserStatus3 = new Status(dummyUser, "I would like to mention @AllenAnderson and @HelenHopwell", "February 17 2021 9:18 PM", Arrays.asList(user1, user16));
    private final Status dummyUserStatus4 = new Status(dummyUser, "1Hello Status!", "Feburary 17 2021 9:19 PM", new ArrayList<User>());
    private final Status dummyUserStatus5 = new Status(dummyUser, "1Goodbye Status!", "Feburary 17 2021 9:20 PM", new ArrayList<User>());
    private final Status dummyUserStatus6 = new Status(dummyUser, "1I would like to mention @AllenAnderson and @HelenHopwell", "February 17 2021 9:21 PM", Arrays.asList(user1, user16));
    private final Status dummyUserStatus7 = new Status(dummyUser, "2Hello Status!", "Feburary 17 2021 9:22 PM", new ArrayList<User>());
    private final Status dummyUserStatus8 = new Status(dummyUser, "2Goodbye Status!", "Feburary 17 2021 9:23 PM", new ArrayList<User>());
    private final Status dummyUserStatus9 = new Status(dummyUser, "2I would like to mention @AllenAnderson and @HelenHopwell", "February 17 2021 9:24 PM", Arrays.asList(user1, user16));
    private final Status dummyUserStatus10 = new Status(dummyUser, "3Hello Status!", "Feburary 17 2021 9:25 PM", new ArrayList<User>());
    private final Status dummyUserStatus11 = new Status(dummyUser, "3Goodbye Status!", "Feburary 17 2021 9:26 PM", new ArrayList<User>());
    private final Status dummyUserStatus12 = new Status(dummyUser, "3I would like to mention @AllenAnderson and @HelenHopwell", "February 17 2021 9:27 PM", Arrays.asList(user1, user16));


    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        if (request.getPassword().equals("dummyPassword") && request.getUsername().equals("dummyUserName")){
            User user = new User("Test", "User", "helloWorld",
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            return new LoginResponse(user, new AuthToken());
        }
        else{
            return new LoginResponse("Failed to authenticate user on login");
        }
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

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFollowStartingIndex(request.getLastFollowAlias(), allFollowees);

            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowResponse(responseFollowees, hasMorePages);
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

        List<User> allFollowers = getDummyFollowers();
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

    /**
     * Returns the status that the user specified in the request. Uses information in the request object
     * to limit the number of statuses returned and to return the next set of statuses after any
     * that were returned in the previous request. The current implementation returns generated data
     * and doesn't actually make a network request.
     *
     * @param request contains information about the user whos status are to be returned
     * @return the story response.
     */
    public StoryResponse getStory(StoryRequest request) {
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUserToGet() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyStory();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int storyIndex = getStatusStartingIndex(request.getLastStatusSent(), allStatuses);

            for (int limitCounter = 0; storyIndex < allStatuses.size() && limitCounter < request.getLimit(); storyIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(storyIndex));
            }

            hasMorePages = storyIndex < allStatuses.size();
        }

        return new StoryResponse(true, hasMorePages, responseStatuses);
    }

    /**
     * Determines the index for the first status in the specified 'allStatuses' list that should be
     * returned in the current request. This will be the index of the next status after the specified
     * 'message'.
     *
     * @param message the last message seen.
     * @param allStatuses all of the statuses.
     * @returnthe index of the first status to be returned.
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
     * Returns the list of dummy status for the story. This is written as a separate method to allow
     * mocking of the story.
     * @return
     */
    List<Status> getDummyStory() {
        return Arrays.asList(dummyUserStatus1, dummyUserStatus2, dummyUserStatus3, dummyUserStatus4,
                dummyUserStatus5, dummyUserStatus6, dummyUserStatus7, dummyUserStatus8, dummyUserStatus9,
                dummyUserStatus10, dummyUserStatus11, dummyUserStatus12);
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    /**
     * Returns the list of dummy follower data. This is written as a separate method to allow
     * mocking of the followers.
     *
     * @return the followers.
     */
    List<User> getDummyFollowers() {
        return Arrays.asList(user1, user3, user6, user9, user12, user15, user18, user20);
    }

    /**
     * Logs out a user with an AuthToken
     * @param logoutRequest
     * @return
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        LogoutResponse logoutResponse = null;

        if (logoutRequest.getAuthToken() == null) {
            logoutResponse = new LogoutResponse(false, "Invalid AuthToken");
        } else {
            logoutResponse = new LogoutResponse(true);
        }

        return  logoutResponse;

    }
}
