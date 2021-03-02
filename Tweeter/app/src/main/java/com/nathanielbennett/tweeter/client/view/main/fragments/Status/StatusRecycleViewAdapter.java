package com.nathanielbennett.tweeter.client.view.main.fragments.Status;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.presenter.TemplatePresenter;
import com.nathanielbennett.tweeter.client.view.main.fragments.TemplateItemHolder;
import com.nathanielbennett.tweeter.client.view.main.fragments.TemplateRecyclerViewAdapter;

import java.util.ArrayList;

abstract public class StatusRecycleViewAdapter extends TemplateRecyclerViewAdapter<Status> {

    protected TemplatePresenter presenter;
    protected String lastStatusMessage;

    /**
     * Creates an instance.
     *
     * @param context The context passed in from the os.
     * @param presenter The presenter used to make requests.
     * @param lastStatusMessage The last seen message.
     */
    public StatusRecycleViewAdapter(Context context, TemplatePresenter presenter, String lastStatusMessage, User user) {
        this.context = context;
        this.presenter = presenter;
        this.lastStatusMessage = lastStatusMessage;
        this.user = user;
    }

    /**
     * Setter for lastStatusMessage.
     *
     * @param lastStatusMessage the last seen status message.
     */
    public void setLastStatusMessage(String lastStatusMessage) {
        this.lastStatusMessage = lastStatusMessage;
    }

    /**
     * Generates a new Item Holder.
     *
     * @param view the required view.
     * @param viewType The view type.
     * @return a new StatusItemHolder.
     */
    @Override
    public TemplateItemHolder<Status> generateItemHolder(@NonNull View view, int viewType) {
        return new StatusItemHolder(view, viewType, context);
    }

    /**
     * Adds a loading footer to indicate data is loading.
     */
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



    @Override
    abstract public void loadMoreItems();
}
