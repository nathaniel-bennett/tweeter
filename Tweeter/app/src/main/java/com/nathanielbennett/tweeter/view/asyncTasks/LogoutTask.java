package com.nathanielbennett.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.presenter.MainPresenter;

import java.io.IOException;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse> {

    private final MainPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void logoutSuccessful(LogoutResponse logoutResponse);
        void logoutUnsuccessful(LogoutResponse logoutResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param observer the observer who wants to be notified when this task completes.
     * @param presenter the presenter this task should use to login.
     */
    public LogoutTask(Observer observer, MainPresenter presenter) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.observer = observer;
        this.presenter = presenter;
    }

    /**
     * The method that is invoked on the background thread to logout a user.
     * @param logoutRequests
     * @return
     */
    @Override
    protected LogoutResponse doInBackground(LogoutRequest... logoutRequests) {
        LogoutResponse logoutResponse = null;
        try {
            logoutResponse = presenter.logout(logoutRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }
        return logoutResponse;
    }

    /**
     * The method that the android framework calls after the task is complete.
     * @param logoutResponse
     */
    @Override
    protected void onPostExecute(LogoutResponse logoutResponse) {
        if (exception != null) {
            observer.handleException(exception);
        } else if (logoutResponse.isSuccess()) {
            observer.logoutSuccessful(logoutResponse);
        } else {
            observer.logoutUnsuccessful(logoutResponse);
        }
    }


}
