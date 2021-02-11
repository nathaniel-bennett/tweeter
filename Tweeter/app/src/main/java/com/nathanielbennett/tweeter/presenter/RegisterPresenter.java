package com.nathanielbennett.tweeter.presenter;


import java.io.IOException;

/**
 * The presenter for the registration functionality of the application.
 */
public class RegisterPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public RegisterPresenter(RegisterPresenter.View view) {
        this.view = view;
    }

    /**
     * Makes a login request.
     *
     * @param loginRequest the request.
     */
    /*
    public RegisterResponse register(LoginRequest loginRequest) throws IOException {
        LoginService loginService = new LoginService();
        return loginService.login(loginRequest);
    }
     */

}
