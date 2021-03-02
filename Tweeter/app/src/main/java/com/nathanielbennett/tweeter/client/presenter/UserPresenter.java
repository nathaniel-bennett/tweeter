package com.nathanielbennett.tweeter.client.presenter;


import com.nathanielbennett.tweeter.client.model.service.FollowService;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

import java.io.IOException;

public class UserPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view
     */
    public interface View {

    }

    /**
     * creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public UserPresenter(View view) {
        this.view = view;
    }

    /**
     * Attempts to have the logged-in user follow a given user.
     *
     * @param request A request containing the logged-in user's credentials and the username of
     *                a user to follow.
     * @return A response indicating success, or a response containing an error message on failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public FollowUserResponse followUser(FollowUserRequest request) throws IOException {
        return getFollowService().follow(request);
    }

    /**
     * Attempts to have the logged-in user unfollow a given user.
     *
     * @param request A request containing the logged-in user's credentials and the username of
     *                a user to unfollow.
     * @return A response indicating success, or a response containing an error message on failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) throws IOException {
        return getFollowService().unfollow(request);
    }

    /**
     * Checks to see whether a given user is being followed by the logged-in user or not.
     *
     * @param request A request containing the logged-in user and another user to check.
     * @return A response indicating whether the user is being followed on success, or an error
     * message on failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public CheckFollowingResponse checkFollowing(CheckFollowingRequest request) throws IOException {
        return getFollowService().checkFollowStatus(request);
    }


    /**
     * Returns an instance of {@link FollowService}. Allows mocking of the FollowService class
     * for testing purposes. All usages of FollowService should get their FollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    protected FollowService getFollowService() {
        return new FollowService();
    }
}
