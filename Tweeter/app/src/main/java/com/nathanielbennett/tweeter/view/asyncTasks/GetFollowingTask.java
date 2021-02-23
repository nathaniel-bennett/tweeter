package com.nathanielbennett.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.presenter.FollowingPresenter;

import java.io.IOException;

/**
 * An {@link AsyncTask} for retrieving followees for a user.
 */
public class GetFollowingTask extends TemplateTask {

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followeesRetrieved(FollowResponse followResponse);
        void handleException(Exception exception);
    }

    private final FollowingPresenter presenter;
    private final Observer observer;

    /**
     * Creates an instance.
     *
     * @param presenter The presenter to query for following information.
     * @param observer The view to notify on completion of request or error.
     */
    public GetFollowingTask(FollowingPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * Method called to make request to the back end.
     *
     * @param request the story request.
     * @return The response from the back end.
     * @throws IOException
     */
    @Override
    protected TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        FollowResponse response = presenter.getFollowing((FollowRequest) request);
        if (response.isSuccess()) {
            loadUserImages(response.getRequestedUsers());
        }

        return response;
    }

    /**
     * Method called when the task was successful.
     *
     * @param response The response from the back end.
     */
    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.followeesRetrieved((FollowResponse) response);
    }

    /**
     * Method called when the task was unsuccessful.
     *
     * @param response The response from the back end.
     */
    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        // Intentionally left blank
        // TODO: ADD SOME ERROR STUFF HERE
    }

    /**
     * Method called when the task threw an exception.
     *
     * @param ex The exceptoin thrown.
     */
    @Override
    protected void handleException(Exception ex) {
        observer.handleException(ex);
    }

}
