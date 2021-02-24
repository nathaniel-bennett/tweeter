package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.service.LogoutService;
import com.nathanielbennett.tweeter.model.service.PostService;
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
     * @return A success response if the user was deauthenticated, or an error message on failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        return getLogoutService().logout(logoutRequest);
    }

    /**
     * returns an instance of {@link LogoutService}. Allows mocking of the LogoutService class for
     * testing purposes. All usages of LogoutService should get their LogoutService instance from this
     * method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    protected LogoutService getLogoutService() {
        return new LogoutService();
    }
}
