package com.nathanielbennett.tweeter.view.login.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.presenter.RegisterPresenter;

public class RegisterFragment extends Fragment implements RegisterPresenter.View {

    private RegisterPresenter presenter;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        presenter = new RegisterPresenter(this);

        return view;
    }
}
