package com.nathanielbennett.tweeter.view.asyncTasks;


import java.io.IOException;

import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.Request;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.model.service.response.Response;
import com.nathanielbennett.tweeter.presenter.LoginPresenter;

public class LoginTask extends TemplateTask {

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
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
    public Response performTask(Request request) throws IOException {
        return presenter.login((LoginRequest) request);
    }

    @Override
    public void taskSuccessful(Response response) {
        observer.loginSuccessful((LoginResponse) response);
    }

    @Override
    public void taskUnsuccessful(Response response) {
        observer.loginUnsuccessful((LoginResponse) response);
    }

    @Override
    public void handleException(Exception ex) {
        observer.loginException(ex);
    }
}
