package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.service.StatusService.StoryService;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;

import java.io.IOException;

public class StoryPresenter implements TemplatePresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates.
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which the class is the presenter.
     */
    public StoryPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a request to the story service for the specified data.
     *
     * @param request the request to be made to the service.
     * @return the response from the service.
     * @throws IOException
     */
    public StatusResponse getStory(StatusRequest request) throws IOException {
        StoryService storyService = getStoryService();
        return storyService.makeServerRequest(request);
    }

    /**
     * retusn an instance of {@link StoryService}. Allows mocking of the StoryService class for
     * testing purposes. All usages of StoryService should get their StoryService instance from this
     * method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    StoryService getStoryService() {
        return new StoryService();
    }
}
