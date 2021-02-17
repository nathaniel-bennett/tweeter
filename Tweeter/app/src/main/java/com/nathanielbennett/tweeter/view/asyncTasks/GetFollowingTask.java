package com.nathanielbennett.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.Request;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.Response;
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

    public GetFollowingTask(FollowingPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected Response performTask(Request request) throws IOException {
        return presenter.getFollowing((FollowRequest) request);
    }

    @Override
    protected void taskSuccessful(Response response) {
        observer.followeesRetrieved((FollowResponse) response);
    }

    @Override
    protected void taskUnsuccessful(Response response) {
        // Intentionally left blank
        // TODO: ADD SOME ERROR STUFF HERE
    }

    @Override
    protected void handleException(Exception ex) {
        observer.handleException(ex);
    }

}
