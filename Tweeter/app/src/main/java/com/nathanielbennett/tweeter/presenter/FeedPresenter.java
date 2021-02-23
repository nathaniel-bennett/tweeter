package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.service.FeedService;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

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
     */
    public StatusResponse getFeed(StatusRequest request) {
        FeedService feedService = getFeedService();
        return feedService.fetchFeed(request);
    }

    /**
     * Returns an instance of {@link FeedService}. Allows mocking of the StoryService class for
     * purposes. All usages of StoryService should get their StoryService instance from this method
     * to allow for mocking of the instance.
     * @return a FeedService object that can be used.
     */
    FeedService getFeedService() {
        return new FeedService();
    }

}
