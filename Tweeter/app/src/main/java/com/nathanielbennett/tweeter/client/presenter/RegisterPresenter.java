package com.nathanielbennett.tweeter.client.presenter;


import com.nathanielbennett.tweeter.client.model.service.RegisterService;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;

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
     * @param request the request to register.
     * @return user and authorization credentials on success, or an error message on failure.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public RegisterResponse register(RegisterRequest request) throws IOException {
        return getRegisterService().register(request);
    }

    /**
     * Returns an instance of {@link RegisterService}. Allows mocking of the RegisterService class
     * for testing purposes. All usages of RegisterService should get their RegisterService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    protected RegisterService getRegisterService() {
        return new RegisterService();
    }
}
