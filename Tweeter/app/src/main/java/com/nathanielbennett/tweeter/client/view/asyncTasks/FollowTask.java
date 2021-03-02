package com.nathanielbennett.tweeter.client.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.client.presenter.UserPresenter;

import java.io.IOException;

public class FollowTask extends TemplateTask {

    public interface Observer extends TemplateTask.Observer {
        void followUserSuccessful(FollowUserResponse followUserResponse);
        void followUserUnsuccessful(FollowUserResponse followUserResponse);
        void followUserException(Exception ex);
    }

    private final UserPresenter presenter;
    private final Observer observer;

    /**
     * Creates an instance.
     *
     * @param observer the observer who wants to be notified when this task completes.
     * @param presenter the presenter this task should use to follow a user.
     */
    public FollowTask(Observer observer, UserPresenter presenter) {
        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        return presenter.followUser((FollowUserRequest) request);
    }

    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.followUserSuccessful((FollowUserResponse) response);
    }

    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        observer.followUserUnsuccessful((FollowUserResponse) response);
    }

    @Override
    protected void handleException(Exception ex) {
        observer.followUserException(ex);
    }
}
