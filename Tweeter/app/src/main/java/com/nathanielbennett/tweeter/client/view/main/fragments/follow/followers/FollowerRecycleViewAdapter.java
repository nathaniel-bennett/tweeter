package com.nathanielbennett.tweeter.client.view.main.fragments.follow.followers;

import android.content.Context;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.client.presenter.FollowersPresenter;
import com.nathanielbennett.tweeter.client.presenter.TemplatePresenter;
import com.nathanielbennett.tweeter.client.view.asyncTasks.GetFollowersTask;
import com.nathanielbennett.tweeter.client.view.main.fragments.follow.FollowRecycleViewAdapter;

public class FollowerRecycleViewAdapter extends FollowRecycleViewAdapter {

    private GetFollowersTask.Observer observer;
    /**
     * Creates an instance and loads the first page of data
     *
     * @param context
     * @param presenter
     * @param user
     * @param observer
     */
    public FollowerRecycleViewAdapter(Context context, TemplatePresenter presenter, User user, GetFollowersTask.Observer observer) {
        super(context, presenter, user);
        this.observer = observer;
        loadMoreItems();

    }

    @Override
    public void loadMoreItems() {
        if (this.isLoading) {
            return; // Still loading items...
        }

        this.isLoading = true;
        addLoadingFooter();

        GetFollowersTask getFollowersTask = new GetFollowersTask((FollowersPresenter) presenter, observer);
        FollowRequest request = new FollowRequest(user.getAlias(), PAGE_SIZE, (lastFollow ==
                null ? null : lastFollow.getAlias()));
        getFollowersTask.execute(request);
    }
}