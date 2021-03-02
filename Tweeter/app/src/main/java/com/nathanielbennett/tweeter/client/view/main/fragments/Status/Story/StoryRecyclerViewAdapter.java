package com.nathanielbennett.tweeter.client.view.main.fragments.Status.Story;

import android.content.Context;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.client.presenter.StoryPresenter;
import com.nathanielbennett.tweeter.client.presenter.TemplatePresenter;
import com.nathanielbennett.tweeter.client.view.asyncTasks.GetStoryTask;
import com.nathanielbennett.tweeter.client.view.main.fragments.Status.StatusRecycleViewAdapter;

public class StoryRecyclerViewAdapter extends StatusRecycleViewAdapter {


    private GetStoryTask.Observer observer;

    /**
     * Creates an instance.
     *
     * @param context           The context passed in from the os.
     * @param presenter         The presenter used to make requests.
     * @param lastStatusMessage The last seen message.
     * @param observer          The observer to notify when tasks are done.
     * @param user              The user who is associated with this fragment.
     */
    public StoryRecyclerViewAdapter(Context context, TemplatePresenter presenter, String lastStatusMessage, GetStoryTask.Observer observer, User user) {
        super(context, presenter, lastStatusMessage, user);
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

        GetStoryTask getStoryTask = new GetStoryTask((StoryPresenter) presenter, observer);
        StatusRequest request = new StatusRequest(user, PAGE_SIZE, lastStatusMessage);
        getStoryTask.execute(request);
    }
}
