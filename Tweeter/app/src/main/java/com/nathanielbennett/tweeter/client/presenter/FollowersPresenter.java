package com.nathanielbennett.tweeter.client.presenter;


import com.nathanielbennett.tweeter.client.model.service.FollowersServiceProxy;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

import java.io.IOException;

public class FollowersPresenter implements TemplatePresenter {

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
     * */
    public FollowersPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the users that the user specified in the request. Uses information in the request
     * object to limit the number of followers returned and to return the next set of followers
     * after any that were returned in a previous set.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public FollowResponse getFollowers(FollowRequest request) throws IOException {
        FollowersServiceProxy followersServiceProxy = getFollowersService();
        return followersServiceProxy.fetchFollowers(request);
    }

    /**
     * Returns an instance of {@link FollowersServiceProxy}. Allows mocking of the FollowingService class
     * for testing purposes.
     * @return a FollowersService that can be used.
     */
    protected FollowersServiceProxy getFollowersService() {
        return new FollowersServiceProxy();
    }
}
