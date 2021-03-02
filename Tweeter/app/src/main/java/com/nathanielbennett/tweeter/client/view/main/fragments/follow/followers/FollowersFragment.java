package com.nathanielbennett.tweeter.client.view.main.fragments.follow.followers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.client.presenter.FollowersPresenter;
import com.nathanielbennett.tweeter.client.view.asyncTasks.GetFollowersTask;
import com.nathanielbennett.tweeter.client.view.main.fragments.TemplateFragment;
import com.nathanielbennett.tweeter.client.view.main.fragments.follow.FollowRecycleViewAdapter;

import java.util.List;

public class FollowersFragment extends TemplateFragment<User> implements FollowersPresenter.View,
        GetFollowersTask.Observer {

    private static final String LOG_TAG = "FollowersFragment";

    private RecyclerView followersRecyclerView;

    /**
     * Static function used to instantiate a FollowersFragment.
     * @param user The user we want the followers for.
     * @param authToken The authToken for the session.
     * @return a new FollowersFragment.
     */
    public static FollowersFragment newInstance(User user, AuthToken authToken) {
        FollowersFragment fragment = new FollowersFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Creates a new Followersfragment view.
     * @param inflater The inflater passed from the OS
     * @param container The container passed from the OS
     * @param savedInstanceState The bundle passed from the OS
     * @return new followersfragment view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        //no inspection Constantconditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FollowersPresenter(this);

        this.followersRecyclerView = view.findViewById(R.id.followersRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        this.followersRecyclerView.setLayoutManager(layoutManager);

        this.recyclerViewAdapter = new FollowerRecycleViewAdapter(this.getContext(), presenter, user, this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(followersRecyclerView.getContext(), layoutManager.getOrientation());

        this.followersRecyclerView.addItemDecoration(dividerItemDecoration);
        this.followersRecyclerView.setAdapter(recyclerViewAdapter);
        this.followersRecyclerView.addOnScrollListener(getOnScrollListener(layoutManager));

        return view;
    }

    @Override
    public void followersRetrieved(FollowResponse followersResponse) {
        List<User> followers = followersResponse.getRequestedUsers();
        User lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;

        FollowRecycleViewAdapter followRecycleViewAdapter = (FollowRecycleViewAdapter) recyclerViewAdapter;

        followRecycleViewAdapter.setLastFollow(lastFollower);
        followRecycleViewAdapter.setHasMorePages(followersResponse.getHasMorePages());
        followRecycleViewAdapter.setLoading(false);
        followRecycleViewAdapter.removeLoadingFooter();
        followRecycleViewAdapter.addItems(followers);
    }

    @Override
    public void followersNotRetrieved(FollowResponse followersResponse) {
        // TODO IMPLEMENT ME PLEASE
    }

    @Override
    public void handleException(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
        recyclerViewAdapter.removeLoadingFooter();
        recyclerViewAdapter.setLoading(false);
        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
