package com.nathanielbennett.tweeter.view.login.signin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.presenter.RegisterPresenter;
import com.nathanielbennett.tweeter.presenter.SigninPresenter;

public class SigninFragment extends Fragment implements SigninPresenter.View {

    private SigninPresenter presenter;

    public static SigninFragment newInstance() {
        SigninFragment fragment = new SigninFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        presenter = new SigninPresenter(this);

        return view;
    }

}
