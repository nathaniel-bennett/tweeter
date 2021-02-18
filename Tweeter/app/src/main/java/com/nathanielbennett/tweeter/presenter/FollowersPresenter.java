package com.nathanielbennett.tweeter.presenter;


import com.nathanielbennett.tweeter.model.service.FollowService.FollowersService;
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
     */
    public FollowResponse getFollowers(FollowRequest request) throws IOException {
        FollowersService followersService = getFollowersService();
        return followersService.makeServerRequest(request);
    }

    /**
     * Returns an instance of {@link FollowersService}. Allows mocking of the FollowingService class
     * for testing purposes.
     * @return
     */
    FollowersService getFollowersService() {
        return new FollowersService();
    }
}