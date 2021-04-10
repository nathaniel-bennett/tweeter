package com.nathanielbennett.tweeter.client.view.main.fragments.Status.Feed;

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
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.client.presenter.FeedPresenter;
import com.nathanielbennett.tweeter.client.view.asyncTasks.GetFeedTask;
import com.nathanielbennett.tweeter.client.view.main.LoggedInActivity;
import com.nathanielbennett.tweeter.client.view.main.fragments.TemplateFragment;

import java.util.List;

public class FeedFragment extends TemplateFragment<Status> implements FeedPresenter.View,
        GetFeedTask.Observer {

    private static final String LOG_TAG = "FeedFragment";

    /**
     * Called to create a new instance.
     *
     * @param user The user related to the Story
     * @param authToken The authToken for the session.
     * @return a new instance.
     */
    public static FeedFragment newInstance(User user, AuthToken authToken) {
        FeedFragment fragment = new FeedFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(LoggedInActivity.AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a new Feed fragment view.
     *
     * @param inflater The inflater passed from the OS.
     * @param container The container passed from the OS.
     * @param savedInstanceState The savedInstanceState passed from the OS.
     *
     * @return new Feed fragment view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        feedRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(feedRecyclerView.getContext(), layoutManager.getOrientation());
        feedRecyclerView.addItemDecoration(dividerItemDecoration);

        recyclerViewAdapter = new FeedRecyclerViewAdapter(this.getActivity(), presenter, "", this, user);
        feedRecyclerView.setAdapter(recyclerViewAdapter);
        feedRecyclerView.addOnScrollListener(getOnScrollListener(layoutManager));

        return view;
    }

    @Override
    public void feedRetrieved(StatusResponse response) {
        List<Status> statuses = response.getStatuses();
        String lastStatusMessage = (statuses != null && statuses.size() > 0) ? statuses.get(statuses.size() - 1).getStatusMessage() : null;

        FeedRecyclerViewAdapter adapter = (FeedRecyclerViewAdapter) recyclerViewAdapter;

        adapter.setLastStatusMessage(lastStatusMessage);
        adapter.setHasMorePages(response.getHasMorePages());
        adapter.setLoading(false);
        adapter.removeLoadingFooter();
        adapter.addItems(statuses);
    }

    @Override
    public void feedNotRetrieved(StatusResponse response) {
        Log.e(LOG_TAG, response.getErrorMessage());
        recyclerViewAdapter.removeLoadingFooter();
        recyclerViewAdapter.setLoading(false);
        Toast.makeText(getContext(), response.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        recyclerViewAdapter.removeLoadingFooter();
        recyclerViewAdapter.setLoading(false);
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }


}
