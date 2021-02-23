package com.nathanielbennett.tweeter.view.main;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.presenter.MainPresenter;
import com.nathanielbennett.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class UserActivity extends LoggedInActivityTemplate implements MainPresenter.View {

    public static final String SELECTED_USER_KEY = "user";

    User selectedUser;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        selectedUser = (User) getIntent().getSerializableExtra(SELECTED_USER_KEY);
        if(selectedUser == null) {
            throw new RuntimeException("User not passed to activity");
        }

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        /*
        if (authToken == null) {
            throw new RuntimeException("Auth Token not passed to activity");
        }
         */
        mainPresenter = new MainPresenter(this);



        UserSectionsPagerAdapter userSectionsPagerAdapter = new UserSectionsPagerAdapter(this, getSupportFragmentManager(), selectedUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(userSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Button button = findViewById(R.id.followButton);
        button.setOnClickListener(view -> {
            if (button.getText() == getResources().getString(R.string.followButton)) {
                button.setText(R.string.unfollowButton);
                button.setBackgroundColor(getResources().getColor(R.color.red));
            }
            else{
                button.setText(R.string.followButton);
                button.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(selectedUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(selectedUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(selectedUser.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, 42));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, 27));
    }
}