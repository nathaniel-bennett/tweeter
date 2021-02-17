package com.nathanielbennett.tweeter.view.main.fragments.follow.followers;

import android.content.Context;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowersRequest;
import com.nathanielbennett.tweeter.presenter.FollowersPresenter;
import com.nathanielbennett.tweeter.presenter.TemplatePresenter;
import com.nathanielbennett.tweeter.view.asyncTasks.GetFollowersTask;
import com.nathanielbennett.tweeter.view.main.fragments.follow.FollowRecycleViewAdapter;

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
        this.isLoading = true;
        addLoadingFooter();

        GetFollowersTask getFollowersTask = new GetFollowersTask((FollowersPresenter) presenter, observer);
        FollowersRequest request = new FollowersRequest(user.getAlias(), PAGE_SIZE, (lastFollow ==
                null ? null : lastFollow.getAlias()));
        getFollowersTask.execute(request);
    }
}
