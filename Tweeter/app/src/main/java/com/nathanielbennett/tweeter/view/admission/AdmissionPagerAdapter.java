package com.nathanielbennett.tweeter.view.admission;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.view.admission.register.RegisterFragment;
import com.nathanielbennett.tweeter.view.admission.login.LoginFragment;

public class AdmissionPagerAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.login_label, R.string.register_label};
    private static final int SIGNIN_FRAGMENT_POSITION = 0;
    // private static final int REGISTER_FRAGMENT_POSITION = 1;
    private Context mContext;

    public AdmissionPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == SIGNIN_FRAGMENT_POSITION) {
            return new LoginFragment();
        } else {
            return new RegisterFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
