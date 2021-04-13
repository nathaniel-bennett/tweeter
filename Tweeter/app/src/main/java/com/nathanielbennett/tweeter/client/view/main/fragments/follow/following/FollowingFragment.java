package com.nathanielbennett.tweeter.client.view.main.fragments.follow.following;

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
import com.nathanielbennett.tweeter.client.presenter.FollowingPresenter;
import com.nathanielbennett.tweeter.client.view.asyncTasks.GetFollowingTask;
import com.nathanielbennett.tweeter.client.view.main.fragments.TemplateFragment;
import com.nathanielbennett.tweeter.client.view.main.fragments.follow.FollowRecycleViewAdapter;

import java.util.List;

public class FollowingFragment extends TemplateFragment<User> implements FollowingPresenter.View,
        GetFollowingTask.Observer {

    private static final String LOG_TAG = "FollowingFragment";

    private RecyclerView followingRecyclerView;

    /**
     * Static function used to instantiate a FollowingFragment.
     * @param user The user we want the folowees for.
     * @param authToken The authToken for the session.
     * @return a new FollowingFragment.
     */
    public static FollowingFragment newInstance(User user, AuthToken authToken) {
        FollowingFragment fragment = new FollowingFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a new following fragment view.
     * @param inflater The inflater passed from the OS
     * @param container The container passed from the OS
     * @param savedInstanceState The savedInstanceState passed from the OS
     *
     * @return new following fragment view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FollowingPresenter(this);

        this.followingRecyclerView = view.findViewById(R.id.followingRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followingRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(followingRecyclerView.getContext(), layoutManager.getOrientation());

        this.followingRecyclerView.addItemDecoration(dividerItemDecoration);

        recyclerViewAdapter = new FollowingRecycleViewAdapter(this.getContext(),
                (FollowingPresenter) presenter, user, this);
        followingRecyclerView.setAdapter(recyclerViewAdapter);

        followingRecyclerView.addOnScrollListener(getOnScrollListener(layoutManager));

        return view;

    }

    /**
     * Is called when the task receives the response from the database.
     * This method parses the response and adds the appropriate data.
     * @param followResponse The response from the getFolloweesTask.
     */
    @Override
    public void followeesRetrieved(FollowResponse followResponse) {
        List<User> followees = followResponse.getRequestedUsers();
        User lastFollowee = (followees.size() > 0) ? followees.get(followees.size() -1) : null;

        FollowRecycleViewAdapter followRecycleViewAdapter = (
                FollowRecycleViewAdapter) recyclerViewAdapter;

        followRecycleViewAdapter.setLastFollow(lastFollowee);
        followRecycleViewAdapter.setHasMorePages(followResponse.getHasMorePages());
        followRecycleViewAdapter.setLoading(false);
        if (followRecycleViewAdapter.getItemCount() > 0) {
            followRecycleViewAdapter.removeLoadingFooter();
        }
        followRecycleViewAdapter.addItems(followees);

    }

    @Override
    public void followeesNotRetrieved(FollowResponse response) {
        Log.e(LOG_TAG, response.getErrorMessage());
        if (recyclerViewAdapter.getItemCount() > 0) {
            recyclerViewAdapter.removeLoadingFooter();
        }
        recyclerViewAdapter.setLoading(false);
        Toast.makeText(getContext(), response.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * Is called when the getFolloweesTask receives an exception and handles said exception.
     * @param exception The exception received from the getFolloweesTask.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        if (recyclerViewAdapter.getItemCount() > 0) {
            recyclerViewAdapter.removeLoadingFooter();
        }
        recyclerViewAdapter.setLoading(false);
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
