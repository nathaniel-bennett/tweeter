package com.nathanielbennett.tweeter.client.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.client.presenter.PostPresenter;

import java.io.IOException;

public class PostTask extends TemplateTask {

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void postSuccessful(PostResponse response);
        void postNotSuccessful(PostResponse response);
        void postException(Exception exception);
    }

    private final PostPresenter presenter;
    private final Observer observer;

    public PostTask(PostPresenter presenter, Observer observer) {
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
        return presenter.post((PostRequest) request);
    }

    /**
     * Method called when the task was successful.
     *
     * @param response The response from the backend.
     */
    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.postSuccessful((PostResponse) response);
    }

    /**
     * Method called when the task was unsuccessful.
     *
     * @param response The response from the backend.
     */
    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        observer.postNotSuccessful((PostResponse) response);
    }

    /**
     * Method called when the task threw an exception.
     *
     * @param ex The exception thrown.
     */
    @Override
    protected void handleException(Exception ex) {
        observer.postException(ex);
    }
}
