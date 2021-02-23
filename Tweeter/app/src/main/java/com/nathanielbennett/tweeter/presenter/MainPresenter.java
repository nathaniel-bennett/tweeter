package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.service.LogoutService;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;

import java.io.IOException;

public class MainPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view
     */
    public interface View {

    }

    /**
     * creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public MainPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a login request.
     *
     * @param logoutRequest The user authentication details needed to log one out.
     * @return LogoutResponse
     * @throws IOException
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        LogoutService logoutService = new LogoutService();
        return logoutService.logout(logoutRequest);
    }
}
