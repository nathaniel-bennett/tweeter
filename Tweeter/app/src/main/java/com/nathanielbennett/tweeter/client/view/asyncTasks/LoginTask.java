package com.nathanielbennett.tweeter.client.view.asyncTasks;


import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.client.presenter.LoginPresenter;

import java.io.IOException;

public class LoginTask extends TemplateTask {

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer extends TemplateTask.Observer {
        void loginSuccessful(LoginResponse loginResponse);
        void loginUnsuccessful(LoginResponse loginResponse);
        void loginException(Exception ex);
    }

    private final LoginPresenter presenter;
    private final Observer observer;

    /**
     * Creates an instance.
     *
     * @param observer the observer who wants to be notified when this task completes.
     * @param presenter the presenter this task should use to login.
     */
    public LoginTask(Observer observer, LoginPresenter presenter) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    public TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        LoginResponse response = presenter.login((LoginRequest) request);

        if (response.getSuccess()) {
            loadUserImage(response.getUser());
        }

        return response;
    }

    @Override
    public void taskSuccessful(TweeterAPIResponse response) {
        observer.loginSuccessful((LoginResponse) response);
    }

    @Override
    public void taskUnsuccessful(TweeterAPIResponse response) {
        observer.loginUnsuccessful((LoginResponse) response);
    }

    @Override
    public void handleException(Exception ex) {
        observer.loginException(ex);
    }
}
