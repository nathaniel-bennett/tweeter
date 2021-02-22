package com.nathanielbennett.tweeter.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.presenter.FollowersPresenter;

import java.io.IOException;

public class GetFollowersTask extends TemplateTask {

    public interface Observer {
        void followersRetrieved(FollowResponse followResponse);
        void followersNotRetrieved(FollowResponse followResponse);
        void handleException(Exception ex);
    }

    private final FollowersPresenter presenter;
    private final Observer observer;

    public GetFollowersTask(FollowersPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        FollowResponse response = presenter.getFollowers((FollowRequest) request);
        if (response.isSuccess()) {
            loadUserImages(response.getRequestedUsers());
        }

        return response;
    }

    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.followersRetrieved((FollowResponse) response);
    }

    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        observer.followersNotRetrieved((FollowResponse) response);
    }

    @Override
    protected void handleException(Exception ex) {
        observer.handleException(ex);
    }
}
