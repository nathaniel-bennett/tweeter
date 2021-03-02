package com.nathanielbennett.tweeter.client.view.admission.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.service.request.RegisterRequest;
import com.nathanielbennett.tweeter.model.service.response.RegisterResponse;
import com.nathanielbennett.tweeter.client.presenter.RegisterPresenter;
import com.nathanielbennett.tweeter.client.view.admission.AdmissionTabFragment;
import com.nathanielbennett.tweeter.client.view.asyncTasks.RegisterTask;

import java.io.ByteArrayOutputStream;

public class RegisterFragment extends AdmissionTabFragment implements RegisterPresenter.View, RegisterTask.Observer {

    private final RegisterPresenter presenter;
    private final int PICTURE_REQUEST_CODE = 18;
    private byte[] picture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        TextView registerButton = view.findViewById(R.id.register_profile_picture);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICTURE_REQUEST_CODE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                picture = stream.toByteArray();
                Toast.makeText(getContext(), "Added Picture", Toast.LENGTH_SHORT).show();
            }
        }
    }

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

        RegisterRequest request = new RegisterRequest(firstName, lastName, username, password, picture);
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
