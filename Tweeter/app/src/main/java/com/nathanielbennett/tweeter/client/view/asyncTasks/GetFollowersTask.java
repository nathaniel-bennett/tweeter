package com.nathanielbennett.tweeter.client.view.asyncTasks;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.client.presenter.FollowersPresenter;

import java.io.IOException;
import java.util.ArrayList;

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
        if (response.getRequestedUsers() == null) {
            response.setRequestedUsers(new ArrayList<User>());
        }

        if (response.getSuccess()) {
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
