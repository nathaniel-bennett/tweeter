package com.nathanielbennett.tweeter.view.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.presenter.MainPresenter;
import com.nathanielbennett.tweeter.view.admission.AdmissionActivity;
import com.nathanielbennett.tweeter.view.asyncTasks.LogoutTask;
import com.nathanielbennett.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class UserActivity extends AppCompatActivity implements MainPresenter.View {

    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String USER_KEY = "user";
    private AuthToken authToken;
    private MainPresenter mainPresenter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mainPresenter = new MainPresenter(this);
        user = (User) getIntent().getSerializableExtra(USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        UserSectionsPagerAdapter userSectionsPagerAdapter = new UserSectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(userSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Button button = findViewById(R.id.followButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (button.getText() == getResources().getString(R.string.followButton)) {
                    button.setText(R.string.unfollowButton);
                    button.setBackgroundColor(getResources().getColor(R.color.red));
                }
                else{
                    button.setText(R.string.followButton);
                    button.setBackgroundColor(getResources().getColor(R.color.green));
                }
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, 42));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, 27));
    }
}