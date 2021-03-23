package com.nathanielbennett.tweeter.client.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
import com.nathanielbennett.tweeter.client.presenter.UserPresenter;
import com.nathanielbennett.tweeter.client.view.asyncTasks.CheckFollowingTask;
import com.nathanielbennett.tweeter.client.view.asyncTasks.FollowTask;
import com.nathanielbennett.tweeter.client.view.asyncTasks.TemplateTask;
import com.nathanielbennett.tweeter.client.view.asyncTasks.UnfollowTask;
import com.nathanielbennett.tweeter.client.view.util.ImageUtils;

import java.text.MessageFormat;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class UserActivity extends LoggedInActivity implements UserPresenter.View, FollowTask.Observer, UnfollowTask.Observer, CheckFollowingTask.Observer {

    public static final String SELECTED_USER_KEY = "user";

    private enum FollowingState {
            UNKNOWN,
            FOLLOWING,
            NOT_FOLLOWING,
    }

    TemplateTask followTask = null;
    FollowingState followingState = FollowingState.UNKNOWN;
    Button followButton = null;


    User selectedUser;
    TextView userFollowerCount;

    Toast toast = null;


    private UserPresenter userPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        loggedInUser = (User) getIntent().getSerializableExtra(LOGGED_IN_USER_KEY);
        if (loggedInUser == null) {
            throw new RuntimeException("User not passed to UserActivity");
        }

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        if (authToken == null) {
            throw new RuntimeException("Auth Token not passed to UserActivity");
        }

        selectedUser = (User) getIntent().getSerializableExtra(SELECTED_USER_KEY);
        if(selectedUser == null) {
            throw new RuntimeException("User not passed to activity");
        }


        userPresenter = new UserPresenter(this);


        UserSectionsPagerAdapter userSectionsPagerAdapter = new UserSectionsPagerAdapter(this, getSupportFragmentManager(), selectedUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(userSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        followButton = findViewById(R.id.followButton);
        followButton.setOnClickListener(view -> {
            switch (followingState) {
                case UNKNOWN:
                    if (followTask == null) {
                        followTask = new CheckFollowingTask(this, userPresenter);
                        followTask.execute(new CheckFollowingRequest(loggedInUser.getAlias(), authToken, selectedUser.getAlias()));
                    }
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(this, "Fetching user relationship...", Toast.LENGTH_SHORT);
                    toast.show();
                    break;

                case FOLLOWING:
                    if (followTask == null) {
                        followTask = new UnfollowTask(this, userPresenter);
                        followTask.execute(new UnfollowUserRequest(loggedInUser.getAlias(), authToken, selectedUser.getAlias()));
                    }
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(this, "Unfollowing user...", Toast.LENGTH_SHORT);
                    toast.show();
                    break;

                case NOT_FOLLOWING:
                    if (followTask == null) {
                        followTask = new FollowTask(this, userPresenter);
                        followTask.execute(new FollowUserRequest(loggedInUser.getAlias(), authToken, selectedUser.getAlias()));
                    }
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(this, "Following user...", Toast.LENGTH_SHORT);
                    toast.show();
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(selectedUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(selectedUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(selectedUser.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(MessageFormat.format("Following: {0}", Integer.toString(selectedUser.getFolloweeCount())));

        userFollowerCount = findViewById(R.id.followerCount);
        userFollowerCount.setText(MessageFormat.format("Followers: {0}", Integer.toString(selectedUser.getFollowerCount())));

        // Begin a query to find out if the logged in user is following the selected user or not
        followTask = new CheckFollowingTask(this, userPresenter);
        followTask.execute(new CheckFollowingRequest(loggedInUser.getAlias(), authToken, selectedUser.getAlias()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(LOGGED_IN_USER_KEY, getLoggedInUser());
        intent.putExtra(AUTH_TOKEN_KEY, getAuthToken());

        startActivity(intent);
        finish();
    }


    @Override
    public void checkFollowingSuccessful(CheckFollowingResponse checkFollowingResponse) {
        if (checkFollowingResponse.getFollowingUser()) {
            setFollowing();
        } else {
            setNotFollowing();
        }
    }

    @Override
    public void checkFollowingUnsuccessful(CheckFollowingResponse checkFollowingResponse) {
        setFollowError("Couldn't fetch user relationship: " + checkFollowingResponse.getMessage());
    }

    @Override
    public void checkFollowingException(Exception ex) {
        setFollowError("Exception while fetching user relationship: " + ex.getMessage());
    }



    @Override
    public void followUserSuccessful(FollowUserResponse followUserResponse) {
        setFollowing();
        loggedInUser.setFolloweeCount(loggedInUser.getFolloweeCount()+1);
        selectedUser.setFollowerCount(selectedUser.getFollowerCount()+1);
        userFollowerCount.setText(MessageFormat.format("Followers: {0}", Integer.toString(selectedUser.getFollowerCount())));
    }

    @Override
    public void followUserUnsuccessful(FollowUserResponse followUserResponse) {
        setFollowError("Failed to follow user: " + followUserResponse.getMessage());
    }

    @Override
    public void followUserException(Exception ex) {
        setFollowError("Exception while following user: " + ex.getMessage());
    }



    @Override
    public void unfollowUserSuccessful(UnfollowUserResponse followUserResponse) {
        setNotFollowing();
        loggedInUser.setFolloweeCount(loggedInUser.getFolloweeCount()-1);
        selectedUser.setFollowerCount(selectedUser.getFollowerCount()-1);
        userFollowerCount.setText(MessageFormat.format("Followers: {0}", selectedUser.getFollowerCount()));
    }

    @Override
    public void unfollowUserUnsuccessful(UnfollowUserResponse followUserResponse) {
        setFollowError("Failed to unfollow user: " + followUserResponse.getMessage());
    }

    @Override
    public void unfollowUserException(Exception ex) {
        setFollowError("Exception while unfollowing user: " + ex.getMessage());
    }


    private void setNotFollowing() {
        followingState = FollowingState.NOT_FOLLOWING;
        followButton.setText(R.string.start_following_user_labe);
        followButton.setTextColor(getResources().getColor(R.color.white));
        followButton.setBackgroundColor(getResources().getColor(R.color.green));
        followTask = null;
    }

    private void setFollowing() {
        followingState = FollowingState.FOLLOWING;
        followButton.setText(R.string.following_user_label);
        followButton.setTextColor(getResources().getColor(R.color.black));
        followButton.setBackgroundColor(getResources().getColor(R.color.white));
        followTask = null;
    }

    private void setFollowError(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
        followTask = null;
    }

}