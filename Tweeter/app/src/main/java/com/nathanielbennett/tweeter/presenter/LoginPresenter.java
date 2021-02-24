package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;

import java.io.IOException;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public LoginPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a login request.
     *
     * @param loginRequest the request.
     * @return user credentials if a login is successful, or an error message on failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public LoginResponse login(LoginRequest loginRequest) throws IOException {
        //Log.e("login username", loginRequest.getUsername());
        //Log.e("login password", loginRequest.getPassword());
        LoginService loginService = getLoginService();
        return loginService.login(loginRequest);
    }

    /**
     * returns an instance of {@link LoginService}. Allows mocking of the LoginService class for
     * testing purposes. All usages of LoginService should get their LoginService instance from this
     * method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    protected LoginService getLoginService() {
        return new LoginService();
    }
}
