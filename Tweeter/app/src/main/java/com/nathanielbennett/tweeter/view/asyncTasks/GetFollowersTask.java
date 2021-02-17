package com.nathanielbennett.tweeter.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.FollowersRequest;
import com.nathanielbennett.tweeter.model.service.request.Request;
import com.nathanielbennett.tweeter.model.service.response.FollowersResponse;
import com.nathanielbennett.tweeter.model.service.response.Response;
import com.nathanielbennett.tweeter.presenter.FollowersPresenter;

import java.io.IOException;

public class GetFollowersTask extends TemplateTask{

    public interface Observer {
        void followersRetrieved(FollowersResponse followersResponse);
        void followersNotRetrieved(FollowersResponse followersResponse);
        void handleException(Exception ex);
    }

    private final FollowersPresenter presenter;
    private final Observer observer;

    public GetFollowersTask(FollowersPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected Response performTask(Request request) throws IOException {
        return presenter.getFollowers((FollowersRequest) request);
    }

    @Override
    protected void taskSuccessful(Response response) {
        observer.followersRetrieved((FollowersResponse) response);
    }

    @Override
    protected void taskUnsuccessful(Response response) {
        observer.followersNotRetrieved((FollowersResponse) response);
    }

    @Override
    protected void handleException(Exception ex) {
        observer.handleException(ex);
    }
}
