package com.nathanielbennett.tweeter.view.main.fragments.following;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.presenter.FollowingPresenter;
import com.nathanielbennett.tweeter.view.main.fragments.TemplateFragment;

public class FollowingFragment extends TemplateFragment<User> implements FollowingPresenter.View {

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
     * @param savedInstanceState The bundle passed from the OS
     * @return new following fragment view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FollowingPresenter(this);

        RecyclerView followingRecyclerView = view.findViewById(R.id.followingRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followingRecyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new FollowingRecycleViewAdapter(this.getContext(), (FollowingPresenter) presenter, user);
        followingRecyclerView.setAdapter(recyclerViewAdapter);

        followingRecyclerView.addOnScrollListener(getOnScrollListener(layoutManager));

        return view;

    }


}
