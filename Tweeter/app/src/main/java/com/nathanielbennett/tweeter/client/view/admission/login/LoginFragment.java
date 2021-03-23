package com.nathanielbennett.tweeter.client.view.admission.login;

import android.view.View;
import android.widget.EditText;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.service.request.LoginRequest;
import com.nathanielbennett.tweeter.model.service.response.LoginResponse;
import com.nathanielbennett.tweeter.client.presenter.LoginPresenter;
import com.nathanielbennett.tweeter.client.view.admission.AdmissionTabFragment;
import com.nathanielbennett.tweeter.client.view.asyncTasks.LoginTask;

public class LoginFragment extends AdmissionTabFragment implements LoginPresenter.View,
        LoginTask.Observer {

    private final LoginPresenter presenter;


    public LoginFragment() {
        super();

        presenter = new LoginPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_login;
    }

    @Override
    protected int getAdmissionButtonID() {
        return R.id.login_button;
    }

    @Override
    protected String getAdmissionType() {
        return requireContext().getString(R.string.login_label);
    }

    @Override
    protected void executeAdmissionRequest() {
        View view = requireView();

        EditText usernameField = view.findViewById(R.id.login_username);
        EditText passwordField = view.findViewById(R.id.login_password);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        LoginRequest request = new LoginRequest(username, password);
        LoginTask loginTask = new LoginTask(this, presenter);
        loginTask.execute(request);
    }


    @Override
    public void loginSuccessful(LoginResponse response) {
        transitionToMainMenu(response.getUser(), response.getAuthToken());
    }

    @Override
    public void loginUnsuccessful(LoginResponse response) {
        indicateAdmissionFailure(response.getErrorMessage());
    }

    @Override
    public void loginException(Exception e) {
        indicateAdmissionFailure("exception thrown: " + e.getMessage());
    }
}
