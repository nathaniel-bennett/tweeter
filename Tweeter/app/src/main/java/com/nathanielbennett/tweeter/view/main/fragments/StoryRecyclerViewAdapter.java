package com.nathanielbennett.tweeter.view.main.fragments;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.StoryRequest;
import com.nathanielbennett.tweeter.presenter.StoryPresenter;
import com.nathanielbennett.tweeter.view.asyncTasks.GetStoryTask;

import java.util.ArrayList;

public class StoryRecyclerViewAdapter extends TemplateRecyclerViewAdapter<Status> {

    private StoryPresenter presenter;
    private String lastStatusMessage;
    private GetStoryTask.Observer observer;

    /**
     * Creates an instance.
     *
     * @param presenter The presenter for the adapter.
     */
    public StoryRecyclerViewAdapter(Context context, StoryPresenter presenter, GetStoryTask.Observer observer, User user) {
        this.user = user;
        this.context = context;
        this.presenter = presenter;
        this.observer = observer;
        this.lastStatusMessage = null;
        loadMoreItems();
    }

    /**
     * Setter for the lastStatus.
     *
     * @param lastStatus the last seen status.
     */
    public void setLastStatus(String lastStatus) {
        this.lastStatusMessage = lastStatus;
    }

    /**
     * Creates an instance of the item holder.
     *
     * @param view the view for which the item will be held in.
     * @param viewType the type of view to create.
     * @return a new StoryItemHolder.
     */
    @Override
    public TemplateItemHolder<Status> generateItemHolder(@NonNull View view, int viewType) {
        return new StoryItemHolder(view, viewType, context);
    }

    /**
     * Loads more items for the recycleView to display.
     */
    @Override
    public void loadMoreItems() {
        this.isLoading = true;
        addLoadingFooter();

        GetStoryTask getStoryTask = new GetStoryTask((StoryPresenter) presenter, observer);
        StoryRequest request = new StoryRequest(user, PAGE_SIZE, lastStatusMessage);
        getStoryTask.execute(request);
    }

    @Override
    public void addLoadingFooter() {
        User dummyUser = new User("Dummy", "User", "");
        addItem(new Status(dummyUser, "Status", "Never", new ArrayList<>()));
    }

    /**
     * Returns the type of the view that should be displayed for the item currently at the
     * specified position.
     *
     * @param position the position of the items whose view type is to be returned.
     * @return the view type.
     */
    @Override
    public int getItemViewType(int position) {
        return (position == itemsToDisplay.size() - 1 && isLoading) ? LOADING_DATA_VIEW : STORY_ITEM_VIEW;
    }

    // YEAH THIS CAN BE ABSTRACTED UP THE TREE
    @Override
    public void removeLoadingFooter() {
        removeItem(itemsToDisplay.get(itemsToDisplay.size() - 1));
    }
}
