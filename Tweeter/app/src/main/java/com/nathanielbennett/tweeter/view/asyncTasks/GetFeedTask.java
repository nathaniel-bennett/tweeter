package com.nathanielbennett.tweeter.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.presenter.FeedPresenter;

import java.io.IOException;

public class GetFeedTask extends TemplateTask {

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void feedRetrieved(StatusResponse response);
        void feedNotRetrieved(StatusResponse response);
        void handleException(Exception exception);
    }

    private final FeedPresenter presenter;
    private final Observer observer;

    /**
     * Creates an instance.
     *
     * @param presenter the presenter for this task.
     * @param observer the observer to notify on task complete.
     */
    public GetFeedTask(FeedPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * Method called to make request to backend.
     *
     * @param request the feedRequest.
     * @return The response from the backend.
     * @throws IOException
     */
    @Override
    protected TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        StatusResponse response = presenter.getFeed((StatusRequest) request);
        if (response.isSuccess()) {
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
     * @param response The resposne from the backend.
     */
    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.feedRetrieved((StatusResponse) response);
    }

    /**
     * Method called when the task was unsuccessful.
     *
     * @param response The response from the backend.
     */
    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        observer.feedNotRetrieved((StatusResponse) response);
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
