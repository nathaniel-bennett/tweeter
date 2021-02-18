package com.nathanielbennett.tweeter.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.Request;
import com.nathanielbennett.tweeter.model.service.request.StoryRequest;
import com.nathanielbennett.tweeter.model.service.response.Response;
import com.nathanielbennett.tweeter.model.service.response.StoryResponse;
import com.nathanielbennett.tweeter.presenter.StoryPresenter;

import java.io.IOException;

public class GetStoryTask extends TemplateTask {

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void storyRetrieved(StoryResponse response);
        void storyNotRetrieved(StoryResponse response);
        void handleException(Exception exception);
    }

    private final StoryPresenter presenter;
    private final Observer observer;

    /**
     * Creates an instance.
     * @param presenter
     * @param observer
     */
    public GetStoryTask(StoryPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * Method called to make request to back end.
     *
     * @param request the story request
     * @return The response from the backend.
     * @throws IOException
     */
    @Override
    protected Response performTask(Request request) throws IOException {
        return presenter.getStory((StoryRequest) request);
    }

    /**
     * Method called when the task was successful.
     *
     * @param response The response from the backend.
     */
    @Override
    protected void taskSuccessful(Response response) {
        observer.storyRetrieved((StoryResponse) response);
    }

    /**
     * Method called when the task was unsuccessful.
     *
     * @param response The response from the backend.
     */
    @Override
    protected void taskUnsuccessful(Response response) {
        observer.storyNotRetrieved((StoryResponse) response);
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
