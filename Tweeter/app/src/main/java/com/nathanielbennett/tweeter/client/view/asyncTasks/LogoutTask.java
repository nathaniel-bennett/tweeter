package com.nathanielbennett.tweeter.client.view.asyncTasks;

import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.client.presenter.MainPresenter;

import java.io.IOException;

public class LogoutTask extends TemplateTask {

    private final MainPresenter presenter;
    private final Observer observer;

    public interface Observer extends TemplateTask.Observer {
        void logoutSuccessful(LogoutResponse logoutResponse);
        void logoutUnsuccessful(LogoutResponse logoutResponse);
        void logoutException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param observer the observer who wants to be notified when this task completes.
     * @param presenter the presenter this task should use to login.
     */
    public LogoutTask(Observer observer, MainPresenter presenter) {
        this.observer = observer;
        this.presenter = presenter;
    }



    @Override
    public TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException {
        return presenter.logout((LogoutRequest) request);
    }

    @Override
    public void taskSuccessful(TweeterAPIResponse response) {
        observer.logoutSuccessful((LogoutResponse) response);
    }

    @Override
    public void taskUnsuccessful(TweeterAPIResponse response) {
        observer.logoutUnsuccessful((LogoutResponse) response);
    }

    @Override
    public void handleException(Exception ex) {
        observer.logoutException(ex);
    }
}
