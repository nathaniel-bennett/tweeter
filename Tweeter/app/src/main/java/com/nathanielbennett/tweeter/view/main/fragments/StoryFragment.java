package com.nathanielbennett.tweeter.view.main.fragments;

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
import com.nathanielbennett.tweeter.model.service.response.StoryResponse;
import com.nathanielbennett.tweeter.presenter.StoryPresenter;
import com.nathanielbennett.tweeter.view.asyncTasks.GetStoryTask;

import java.util.List;

public class StoryFragment extends TemplateFragment<Status> implements StoryPresenter.View,
        GetStoryTask.Observer {

    private static final String LOG_TAG = "StoryFragment";

    private RecyclerView storyRecyclerView;

    public static StoryFragment newInstance(User user, AuthToken authToken) {
        StoryFragment fragment = new StoryFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a new story fragment view.
     *
     * @param inflater The inflater passed from the OS.
     * @param container The container passed from the OS.
     * @param savedInstanceState The savedInstanceState passed from the OS.
     *
     * @return new Story fragment view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new StoryPresenter(this);

        this.storyRecyclerView = view.findViewById(R.id.storyRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        storyRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(storyRecyclerView.getContext(), layoutManager.getOrientation());
        this.storyRecyclerView.addItemDecoration(dividerItemDecoration);

        recyclerViewAdapter = new StoryRecyclerViewAdapter(this.getContext(), (StoryPresenter) presenter, this, user);
        storyRecyclerView.setAdapter(recyclerViewAdapter);
        storyRecyclerView.addOnScrollListener(getOnScrollListener(layoutManager));

        return view;
    }

    /**
     * Is called when the task receives the response from the database.
     * This method parses the response and adds the appropriate data.
     *
     * @param response The response from the getStoryTask.
     */
    @Override
    public void storyRetrieved(StoryResponse response) {
        List<Status> statuses = response.getStatuses();
        String lastStatusMessage = (statuses.size() > 0) ? statuses.get(statuses.size() - 1).getStatusMessage() : null;

        StoryRecyclerViewAdapter adapter = (StoryRecyclerViewAdapter) recyclerViewAdapter;

        adapter.setLastStatus(lastStatusMessage);
        adapter.setHasMorePages(response.getHasMorePages());
        adapter.setLoading(false);
        adapter.removeLoadingFooter();
        adapter.addItems(statuses);
    }

    @Override
    public void storyNotRetrieved(StoryResponse response) {
        //TODO SOMETHING NEEDS TO BE DONE IN CASE OF AN ERROR
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        recyclerViewAdapter.removeLoadingFooter();
        recyclerViewAdapter.setLoading(false);
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
