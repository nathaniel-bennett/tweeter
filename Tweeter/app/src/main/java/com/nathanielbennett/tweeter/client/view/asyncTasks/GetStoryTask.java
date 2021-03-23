package com.nathanielbennett.tweeter.client.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.client.presenter.StoryPresenter;

import java.io.IOException;

public class GetStoryTask extends TemplateTask {

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void storyRetrieved(StatusResponse response);
        void storyNotRetrieved(StatusResponse response);
        void handleException(Exception exception);
    }

    private final StoryPresenter presenter;
    private final Observer observer;

    /**
     * Creates an instance.
     * @param presenter The presenter to query for story information.
     * @param observer The view to notify on completion of request or error.
     */
    public GetStoryTask(StoryPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * Method called to make request to backend.
     *
     * @param request the story request.
     * @return The response from the backend.
     * @throws IOException
     */
    @Override
    protected TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        StatusResponse response = presenter.getStory((StatusRequest) request);
        if (response.getSuccess()) {
            loadStatusImages(response.getStatuses());
            for (com.nathanielbennett.tweeter.model.domain.Status status : response.getStatuses()) {
                loadUserImages(status.getMentions());
            }
        }

        return response;
    }

    /**
     * Method called when the task was successful.
     *
     * @param response The response from the backend.
     */
    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.storyRetrieved((StatusResponse) response);
    }

    /**
     * Method called when the task was unsuccessful.
     *
     * @param response The response from the backend.
     */
    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        observer.storyNotRetrieved((StatusResponse) response);
    }

    /**
     * Method called when the task threw an exception.
     *
     * @param ex The exception thrown.
     */
    @Override
    protected void handleException(Exception ex) {
        observer.handleException(ex);
    }
}
