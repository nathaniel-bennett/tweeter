package com.nathanielbennett.tweeter.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
import com.nathanielbennett.tweeter.presenter.UserPresenter;

import java.io.IOException;


public class UnfollowTask extends TemplateTask {

    public interface Observer extends TemplateTask.Observer {
        void unfollowUserSuccessful(UnfollowUserResponse followUserResponse);
        void unfollowUserUnsuccessful(UnfollowUserResponse followUserResponse);
        void unfollowUserException(Exception ex);
    }

    private final UserPresenter presenter;
    private final Observer observer;

    /**
     * Creates an instance.
     *
     * @param observer the observer who wants to be notified when this task completes.
     * @param presenter the presenter this task should use to unfollow a user.
     */
    public UnfollowTask(Observer observer, UserPresenter presenter) {
        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        return presenter.unfollowUser((UnfollowUserRequest) request);
    }

    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.unfollowUserSuccessful((UnfollowUserResponse) response);
    }

    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        observer.unfollowUserUnsuccessful((UnfollowUserResponse) response);
    }

    @Override
    protected void handleException(Exception ex) {
        observer.unfollowUserException(ex);
    }
}
