package com.nathanielbennett.tweeter.presenter;


import com.nathanielbennett.tweeter.model.service.FollowService;
import com.nathanielbennett.tweeter.model.service.FollowingService;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;

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

    public FollowUserResponse followUser(FollowUserRequest request) {
        return getFollowService().followUser(request);
    }

    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        return getFollowService().unfollowUser(request);
    }


    /**
     * Returns an instance of {@link FollowService}. Allows mocking of the FollowService class
     * for testing purposes. All usages of FollowService should get their FollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowService getFollowService() {
        return new FollowService();
    }
}
