package com.nathanielbennett.tweeter.view.main.fragments.follow.following;

import android.content.Context;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.presenter.FollowingPresenter;
import com.nathanielbennett.tweeter.view.asyncTasks.GetFollowingTask;
import com.nathanielbennett.tweeter.view.main.fragments.follow.FollowRecycleViewAdapter;

public class FollowingRecycleViewAdapter extends FollowRecycleViewAdapter {

    private GetFollowingTask.Observer observer;
    /**
     * Creates an instance and loads the first page of data
     *
     * @param context
     * @param presenter
     * @param user
     * @param observer
     */
    public FollowingRecycleViewAdapter(Context context, FollowingPresenter presenter, User user, GetFollowingTask.Observer observer) {
        super(context, presenter, user);
        this.observer = observer;
        loadMoreItems();
    }

    /**
     * Loads more items for the recycleView to display.
     */
    @Override
    public void loadMoreItems() {
        this.isLoading = true;
        addLoadingFooter();

        GetFollowingTask getFollowingTask = new GetFollowingTask((FollowingPresenter) presenter, observer);
        FollowRequest request = new FollowRequest(user.getAlias(), PAGE_SIZE, (lastFollow ==
                null ? null : lastFollow.getAlias()));
        getFollowingTask.execute(request);
    }
}
