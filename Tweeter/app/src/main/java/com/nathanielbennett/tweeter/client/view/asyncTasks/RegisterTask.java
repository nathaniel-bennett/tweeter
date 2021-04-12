package com.nathanielbennett.tweeter.client.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.client.presenter.RegisterPresenter;

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
    protected TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        RegisterRequest registerRequest = (RegisterRequest) request;

        RegisterResponse response = presenter.register((RegisterRequest) request);

        // No sense in loading the picture from S3 if we already have it...
        if (response.getSuccess()) {
            response.getUser().setImageBytes(registerRequest.getImage());
        }

        return response;
    }

    @Override
    protected void taskSuccessful(TweeterAPIResponse response) {
        observer.registrationSuccessful((RegisterResponse) response);
    }

    @Override
    protected void taskUnsuccessful(TweeterAPIResponse response) {
        observer.registrationUnsuccessful((RegisterResponse) response);
    }

    @Override
    protected void handleException(Exception ex) {
        observer.registrationException(ex);
    }
}
