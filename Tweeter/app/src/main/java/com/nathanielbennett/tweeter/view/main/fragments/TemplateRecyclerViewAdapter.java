package com.nathanielbennett.tweeter.view.main.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.User;

import java.util.ArrayList;
import java.util.List;

abstract public class TemplateRecyclerViewAdapter<Item> extends RecyclerView.Adapter<TemplateItemHolder<Item>> {
    protected final List<Item> itemsToDisplay = new ArrayList<>();

    protected static final int LOADING_DATA_VIEW = 0;
    protected static final int ITEM_VIEW = 1;
    protected static final int PAGE_SIZE = 10;

    protected boolean hasMorePages;
    protected boolean isLoading = false;
    protected Context context;
    protected User user; //PASSED FROM THE FRAGMENT

    /**
     * Adds new items to the list from which the RecyclerView retrieves the users it displays
     * and notifies the RecyclerView that items have been added.
     *
     * @param newItems the items to add.
     */
    public void addItems(List<Item> newItems) {
        int startInsertPosition = itemsToDisplay.size();
        itemsToDisplay.addAll(newItems);
        this.notifyItemRangeInserted(startInsertPosition, newItems.size());
    }

    /**
     * Adds a single item to the list from which the RecyclerView retrieves the users it
     * displays and notifies the RecyclerView that an item has been added.
     *
     * @param item the item to add.
     */
    public void addItem(Item item) {
        itemsToDisplay.add(item);
        this.notifyItemInserted(itemsToDisplay.size() - 1);
    }

    /**
     * Removes an item from the list from whcich the RecyclerView retrieves the users it displays
     * and notifies the RecyclerView that an item has been removed.
     * @param item the item to remove
     */
    public void removeItem(Item item) {
        int position = itemsToDisplay.indexOf(item);
        itemsToDisplay.remove(item);
        this.notifyItemRemoved(position);
    }

    /**
     * Binds the item at the specified position unless we are currently loading new data. If
     * we are loading new data, the display at the position will be the data loading footer.
     *
     * @param holder the ViewHolder to which the item should be bound.
     * @param position the position (in th e list of itemsToDisplay) that contains the followee
     *                 to be bound.
     */
    @Override
    public void onBindViewHolder(@NonNull TemplateItemHolder holder, int position) {
        if (!isLoading) {
            holder.bindItem(itemsToDisplay.get(position));
        }
    }

    /**
     * Returns the current number of items available for display.
     * @return the number of items available for display.
     */
    @Override
    public int getItemCount() {
        return itemsToDisplay.size();
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
        return (position == itemsToDisplay.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
    }

    /**
     * Is called when a new item is ready to be inserted into the recycler view.
     * Generates the view and passed the necessary data onto the ItemHolderConstructor.
     * @param parent Parent passed in from OS.
     * @param viewType Type passed in from OS. Indicates if the item is of type loading.
     * @return
     */
    @NonNull
    @Override
    public TemplateItemHolder<Item> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;

        if(viewType == LOADING_DATA_VIEW) {
            view =layoutInflater.inflate(R.layout.loading_row, parent, false);

        } else {
            view = layoutInflater.inflate(R.layout.user_row, parent, false);
        }

        return generateItemHolder(view, viewType);
    }

    /**
     * Used update the loading boolean.
     * @param loading The new state.
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    /**
     * Used to set indicator for new pages.
     * @param hasMorePages The new state.
     */
    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }


    // TODO: FUNCTIONS PASSED TO THE CHILD

    abstract public TemplateItemHolder<Item> generateItemHolder(@NonNull View view, int viewType);

    abstract public void loadMoreItems();

    abstract public void addLoadingFooter();

    abstract public void removeLoadingFooter();


}
