package com.nathanielbennett.tweeter.view.main.fragments.Status.Feed;

import android.content.Context;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.presenter.FeedPresenter;
import com.nathanielbennett.tweeter.presenter.TemplatePresenter;
import com.nathanielbennett.tweeter.view.asyncTasks.GetFeedTask;
import com.nathanielbennett.tweeter.view.main.fragments.Status.StatusRecycleViewAdapter;

public class FeedRecyclerViewAdapter extends StatusRecycleViewAdapter {

    private GetFeedTask.Observer observer;

    /**
     * Creates an instance.
     *
     * @param context           The context passed in from the os.
     * @param presenter         The presenter used to make requests.
     * @param lastStatusMessage The last seen message.
     * @param observer          The observer to notify when tasks are done.
     * @param user              The user who is associated with this fragment.
     */
    public FeedRecyclerViewAdapter(Context context, TemplatePresenter presenter, String lastStatusMessage, GetFeedTask.Observer observer, User user) {
        super(context, presenter, lastStatusMessage, user);
        this.observer = observer;
        loadMoreItems();
    }

    @Override
    public void loadMoreItems() {
        this.isLoading = true;
        addLoadingFooter();

        GetFeedTask getFeedTask = new GetFeedTask((FeedPresenter) presenter, observer);
        StatusRequest request = new StatusRequest(user, PAGE_SIZE, lastStatusMessage);
        getFeedTask.execute(request);
    }
}