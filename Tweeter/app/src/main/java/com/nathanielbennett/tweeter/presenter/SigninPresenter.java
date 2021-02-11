package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;

import java.io.IOException;

/**
 * The presenter for the signin functionality of the application.
 */
public class SigninPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public SigninPresenter(SigninPresenter.View view) {
        this.view = view;
    }

    /**
     * Makes a login request.
     *
     * @param loginRequest the request.
     */
    public LoginResponse login(LoginRequest loginRequest) throws IOException {
        LoginService loginService = new LoginService();
        return loginService.login(loginRequest);
    }
}
