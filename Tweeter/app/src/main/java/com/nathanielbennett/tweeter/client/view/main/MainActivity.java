package com.nathanielbennett.tweeter.client.view.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.LogoutRequest;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.LogoutResponse;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.client.presenter.MainPresenter;
import com.nathanielbennett.tweeter.client.presenter.PostPresenter;
import com.nathanielbennett.tweeter.client.view.admission.AdmissionActivity;
import com.nathanielbennett.tweeter.client.view.asyncTasks.LogoutTask;
import com.nathanielbennett.tweeter.client.view.asyncTasks.PostTask;
import com.nathanielbennett.tweeter.client.view.util.ImageUtils;

import java.text.MessageFormat;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends LoggedInActivity implements MainPresenter.View, LogoutTask.Observer, PostPresenter.View, PostTask.Observer {

    private MainPresenter mainPresenter;
    private MainActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggedInUser = (User) getIntent().getSerializableExtra(LOGGED_IN_USER_KEY);
        if (loggedInUser == null) {
            throw new RuntimeException("User not passed to MainActivity");
        }

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        if (authToken == null) {
            throw new RuntimeException("Auth Token not passed to MainActivity");
        }

        mainPresenter = new MainPresenter(this);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), loggedInUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();

            View v = inflater.inflate(R.layout.fragment_newpost, null);


            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton(R.string.post, (dialog, id) -> {
                        // Get the text
                        EditText text = v.findViewById(R.id.newPost);

                        PostPresenter presenter = new PostPresenter(self);

                        PostRequest request = new PostRequest(text.getText().toString(), loggedInUser.getAlias(), authToken);
                        PostTask postTask = new PostTask(presenter, self);

                        postTask.execute(request);
                                                })
                    .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
            builder.show();
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(loggedInUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(loggedInUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(loggedInUser.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(MessageFormat.format("Following: {0}", Integer.toString(loggedInUser.getFolloweeCount())));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(MessageFormat.format("Followers: {0}", Integer.toString(loggedInUser.getFollowerCount())));

        self = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutMenu){
            LogoutTask logoutTask = new LogoutTask(this, mainPresenter);
            logoutTask.execute(new LogoutRequest(loggedInUser.getAlias(), authToken));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void logoutSuccessful(LogoutResponse logoutResponse) {
        Intent intent = new Intent(MainActivity.this, AdmissionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void logoutUnsuccessful(LogoutResponse logoutResponse) {
        Toast.makeText(this, "Logout failed: " + logoutResponse.getMessage(), Toast.LENGTH_LONG).show();

        // Silently fail--still go back to login page
        Intent intent = new Intent(MainActivity.this, AdmissionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void postSuccessful(PostResponse response) {
        Toast.makeText(this, "Post Successful!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postNotSuccessful(PostResponse response) {
        Toast.makeText(this, "Post unsuccessful: " + response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postException(Exception exception) {
        Toast.makeText(this, "Exception occurred while posting: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logoutException(Exception ex) {
        Toast.makeText(this, "Logout failed: " + ex.getMessage(), Toast.LENGTH_LONG).show();

        // Silently fail--still go back to login page
        Intent intent = new Intent(MainActivity.this, AdmissionActivity.class);
        startActivity(intent);
        finish();
    }
}