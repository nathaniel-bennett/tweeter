package com.nathanielbennett.tweeter.client.presenter;

import com.nathanielbennett.tweeter.client.model.service.FeedServiceProxy;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import java.io.IOException;

public class FeedPresenter implements TemplatePresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's View.
     */
    public interface  View {
        // If needed, specify methods here that will be called on the view in response to model updates.
    }

    /**
     * Creates an instance.]
     *
     * @param view the view for which the class is the presenter.
     */
    public FeedPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a request to the feed service for the specified data.
     *
     * @param request the request to be made to the service.
     * @return the response from the service.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public StatusResponse getFeed(StatusRequest request) throws IOException {
        FeedServiceProxy feedServiceProxy = getFeedService();
        return feedServiceProxy.fetchFeed(request);
    }

    /**
     * Returns an instance of {@link FeedServiceProxy}. Allows mocking of the StoryService class for
     * purposes. All usages of StoryService should get their StoryService instance from this method
     * to allow for mocking of the instance.
     * @return a FeedService object that can be used.
     */
    FeedServiceProxy getFeedService() {
        return new FeedServiceProxy();
    }

}
