package com.nathanielbennett.tweeter.view.main.fragments.follow;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.presenter.TemplatePresenter;
import com.nathanielbennett.tweeter.view.main.fragments.TemplateItemHolder;
import com.nathanielbennett.tweeter.view.main.fragments.TemplateRecyclerViewAdapter;

abstract public class FollowRecycleViewAdapter extends TemplateRecyclerViewAdapter<User> {




    protected TemplatePresenter presenter;
    protected User lastFollow;

    /**
     * Creates an instance.
     * @param context The context passed in from the OS.
     * @param presenter The presenter used to make requests.
     * @param user The last seen user.
     */
    public FollowRecycleViewAdapter(Context context, TemplatePresenter presenter, User user) {
        this.context = context;
        this.presenter = presenter;
        this.user = user;
    }

    /**
     * Setter for the last follow user.
     *
     * @param lastFollow The user last seen.
     */
    public void setLastFollow(User lastFollow) {
        this.lastFollow = lastFollow;
    }

    /**
     * Generates a new Item Holder.
     *
     * @param view the required View.
     * @param viewType The view type.
     * @return a new FollowingItemHolder.
     */
    @Override
    public TemplateItemHolder<User> generateItemHolder(@NonNull View view, int viewType) {
        return new FollowItemHolder(view, viewType, context);
    }

    /**
     * Generates a temporary item to indicating loading.
     */
    @Override
    public void addLoadingFooter() {
        addItem(new User("Dummy", "User",
                ""));
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
        return (position == itemsToDisplay.size() - 1 && isLoading) ? LOADING_DATA_VIEW : FOLLOW_ITEM_VIEW;
    }

    @Override
    abstract public void loadMoreItems();

}
