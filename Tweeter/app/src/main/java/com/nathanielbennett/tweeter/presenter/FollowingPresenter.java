package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.service.FollowingService;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

import java.io.IOException;

/**
 * The presenter for the "following" functionality of the application.
 */
public class FollowingPresenter implements TemplatePresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates

    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public FollowingPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public FollowResponse getFollowing(FollowRequest request) throws IOException {
        FollowingService followingService = getFollowingService();
        return followingService.fetchFollowing(request);
    }

    /**
     * Returns an instance of {@link FollowingService}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    protected FollowingService getFollowingService() {
        return new FollowingService();
    }
}
