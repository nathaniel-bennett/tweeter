package com.nathanielbennett.tweeter.view.admission.register;

import android.view.View;
import android.widget.EditText;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.presenter.RegisterPresenter;
import com.nathanielbennett.tweeter.view.asyncTasks.RegisterTask;
import com.nathanielbennett.tweeter.view.admission.AdmissionTabFragment;

public class RegisterFragment extends AdmissionTabFragment implements RegisterPresenter.View, RegisterTask.Observer {

    private final RegisterPresenter presenter;


    public RegisterFragment() {
        super();

        presenter = new RegisterPresenter(this);
    }


    @Override
    protected void executeAdmissionRequest() {
        View view = requireView();

        EditText firstNameField = view.findViewById(R.id.register_firstname);
        EditText lastNameField = view.findViewById(R.id.register_lastname);
        EditText usernameField = view.findViewById(R.id.register_username);
        EditText passwordField = view.findViewById(R.id.register_password);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        RegisterRequest request = new RegisterRequest(firstName, lastName, username, password);
        RegisterTask registerTask = new RegisterTask(this, presenter);
        registerTask.execute(request);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    protected int getAdmissionButtonID() {
        return R.id.register_button;
    }

    @Override
    protected String getAdmissionType() {
        return requireContext().getString(R.string.register_label);
    }



    @Override
    public void registrationSuccessful(RegisterResponse response) {
        transitionToMainMenu(response.getUser(), response.getAuthToken());
    }

    @Override
    public void registrationUnsuccessful(RegisterResponse response) {
        indicateAdmissionFailure(response.getMessage());
    }

    @Override
    public void registrationException(Exception e) {
        indicateAdmissionFailure("exception thrown: " + e.getMessage());
    }
}
