package com.nathanielbennett.tweeter.view.admission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.view.main.LoggedInActivity;
import com.nathanielbennett.tweeter.view.main.MainActivity;

public abstract class AdmissionTabFragment extends Fragment {

    // NOTE TO CURTIS AND CHRIS: this is using the template method
    // to simplify the Signin and Register tabs at our login page

    private Toast admissionToast;
    private final int layout_id;

    public AdmissionTabFragment() {
        this.layout_id = getLayoutID();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout_id, container, false);

        Button admissionButton = view.findViewById(getAdmissionButtonID());


        admissionButton.setOnClickListener((View v) -> {
            if (admissionToast != null) {
                admissionToast.cancel();
            }

            admissionToast = Toast.makeText(getContext(), "attempting to " + getAdmissionType(), Toast.LENGTH_LONG);
            admissionToast.show();

            // Checking request data validity is the responsibility
            // of the Presenter--we won't do it here

            executeAdmissionRequest();
        });

        return view;
    }

    protected void transitionToMainMenu(User loggedInUser, AuthToken authToken) {
        Intent intent = new Intent(getActivity(), MainActivity.class);

        intent.putExtra(LoggedInActivity.LOGGED_IN_USER_KEY, loggedInUser);
        intent.putExtra(LoggedInActivity.AUTH_TOKEN_KEY, authToken);

        admissionToast.cancel();
        startActivity(intent);
        this.requireActivity().finish();
    }

    protected void indicateAdmissionFailure(String reason) {
        admissionToast.cancel();

        admissionToast = Toast.makeText(getContext(), "Failed to " + getAdmissionType() + " - " + reason, Toast.LENGTH_LONG);
        admissionToast.show();
    }


    protected abstract int getLayoutID();

    protected abstract int getAdmissionButtonID();

    /**
     * Retrieves a user-readable label that designates the type of admission attempt
     * (e.g. "register" for registration and "login" for logging in).
     * @return A human-readable string
     */
    protected abstract String getAdmissionType();

    protected abstract void executeAdmissionRequest();

}




