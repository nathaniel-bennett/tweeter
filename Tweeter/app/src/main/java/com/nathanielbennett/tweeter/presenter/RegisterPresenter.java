package com.nathanielbennett.tweeter.presenter;

import com.nathanielbennett.tweeter.model.service.LoginService;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;

import java.io.IOException;

public class RegisterPresenter {

    private final View view;

    public interface View {

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
