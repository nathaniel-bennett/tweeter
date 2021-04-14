package com.nathanielbennett.tweeter.client.view.main.fragments.Status.Story;

import android.content.Context;
import android.util.Log;

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
     * @param lastStatusMessageTimestamp The last seen message.
     * @param observer          The observer to notify when tasks are done.
     * @param user              The user who is associated with this fragment.
     */
    public StoryRecyclerViewAdapter(Context context, TemplatePresenter presenter, String lastStatusMessageTimestamp, GetStoryTask.Observer observer, User user) {
        super(context, presenter, lastStatusMessageTimestamp, user);
        this.observer = observer;
        loadMoreItems();
    }

    /**
     * Loads more items for the recycleView to display.
     */
    @Override
    public void loadMoreItems() {
        if (this.isLoading) {
            return;
        }
        this.isLoading = true;
        addLoadingFooter();

        GetStoryTask getStoryTask = new GetStoryTask((StoryPresenter) presenter, observer);
        StatusRequest request = new StatusRequest(user.getAlias(), PAGE_SIZE, lastStatusMessageTimestamp);
//        Log.i("lastMessageTimestamp", lastStatusMessageTimestamp);
        getStoryTask.execute(request);
    }
}
