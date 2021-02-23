package com.nathanielbennett.tweeter.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.presenter.UserPresenter;

import java.io.IOException;

public class CheckFollowingTask extends TemplateTask {

    public interface Observer extends TemplateTask.Observer {
        void checkFollowingSuccessful(CheckFollowingResponse checkFollowingResponse);
        void checkFollowingUnsuccessful(CheckFollowingResponse checkFollowingResponse);
        void checkFollowingException(Exception ex);
    }

    private final UserPresenter presenter;
    private final Observer observer;

    /**
     * Creates an instance.
     *
     * @param observer the observer who wants to be notified when this task completes.
     * @param presenter the presenter this task should use to determine whether a user is followed.
     */
    public CheckFollowingTask(Observer observer, UserPresenter presenter) {
        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        return presenter.checkFollowing((CheckFollowingRequest) request);
    }

    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.checkFollowingSuccessful((CheckFollowingResponse) response);
    }

    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        observer.checkFollowingUnsuccessful((CheckFollowingResponse) response);
    }

    @Override
    protected void handleException(Exception ex) {
        observer.checkFollowingException(ex);
    }
}