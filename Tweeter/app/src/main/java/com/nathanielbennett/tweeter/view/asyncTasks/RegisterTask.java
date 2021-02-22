package com.nathanielbennett.tweeter.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.request.Request;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.model.service.response.Response;
import com.nathanielbennett.tweeter.presenter.RegisterPresenter;

import java.io.IOException;

public class RegisterTask extends TemplateTask {

    public interface Observer {
        void registrationSuccessful(RegisterResponse response);
        void registrationUnsuccessful(RegisterResponse response);
        void registrationException(Exception e);
    }

    private final RegisterPresenter presenter;
    private final Observer observer;

    public RegisterTask(Observer observer, RegisterPresenter presenter) {
        this.observer = observer;
        this.presenter = presenter;
    }

    @Override
    public Response performTask(Request request) throws IOException {
        RegisterResponse response = presenter.performRegistration((RegisterRequest) request);

        if (response.isSuccess()) {
            loadUserImage(response.getUser());
        }

        return response;
    }

    @Override
    public void taskSuccessful(Response response) {
        observer.registrationSuccessful((RegisterResponse) response);
    }

    @Override
    public void taskUnsuccessful(Response response) {
        observer.registrationUnsuccessful((RegisterResponse) response);
    }

    @Override
    public void handleException(Exception ex) {
        observer.registrationException(ex);
    }
}
