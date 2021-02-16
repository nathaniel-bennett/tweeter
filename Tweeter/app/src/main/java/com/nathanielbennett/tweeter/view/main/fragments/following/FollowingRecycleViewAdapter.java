package com.nathanielbennett.tweeter.view.main.fragments.following;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowingRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowingResponse;
import com.nathanielbennett.tweeter.presenter.FollowingPresenter;
import com.nathanielbennett.tweeter.view.asyncTasks.GetFollowingTask;
import com.nathanielbennett.tweeter.view.main.fragments.TemplateItemHolder;
import com.nathanielbennett.tweeter.view.main.fragments.TemplateRecyclerViewAdapter;

import java.util.List;

public class FollowingRecycleViewAdapter extends TemplateRecyclerViewAdapter<User> implements GetFollowingTask.Observer{

    private static final String LOG_TAG = "FollowingFragment";


    private FollowingPresenter presenter; //PASSED FROM THE FRAGMENT
    private User lastFollowee;

    /**
     * Creates an instance and loads the first page of data
     * @param context
     * @param presenter
     * @param user
     */
    FollowingRecycleViewAdapter(Context context, FollowingPresenter presenter, User user) {
        this.context = context;
        this.presenter = presenter;
        this.user = user;
        loadMoreItems();

    }

    /**
     * Generates a new Item Holder.
     * @param view the required View.
     * @param viewType The view type.
     * @return a new FollowingItemHolder.
     */
    @Override
    public TemplateItemHolder<User> generateItemHolder(@NonNull View view, int viewType) {
        return new FollowingItemHolder(view, viewType, context);
    }

    /**
     * Generates requests and forwards them onto the task.
     */
    @Override
    public void loadMoreItems() {
        this.isLoading = true;
        addLoadingFooter();

        GetFollowingTask getFollowingTask = new GetFollowingTask(presenter, this);
        FollowingRequest request = new FollowingRequest(user.getAlias(), PAGE_SIZE, (lastFollowee == null ? null : lastFollowee.getAlias()));   //TODO: USER WAS NULL HERE
        getFollowingTask.execute(request);
    }

    /**
     * Generates a temporary item to indicating loading.
     */
    @Override
    public void addLoadingFooter() {
        addItem(new User("Dummy", "User", ""));
    }

    /**
     * Removes temporary item indicating loading.
     */
    @Override
    public void removeLoadingFooter() {
        removeItem(itemsToDisplay.get(itemsToDisplay.size() - 1));
    }


    // INHERRITED FROM OBSERVER

    /**
     * Is called when the task receives the response from the database.
     * This method parses the response and adds the appropriate data.
     * @param followingResponse The response from the getFolloweesTask.
     */
    @Override
    public void followeesRetrieved(FollowingResponse followingResponse) {
        List<User> followees = followingResponse.getFollowees();

        lastFollowee = (followees.size() > 0) ? followees.get(followees.size() -1) : null;
        hasMorePages = followingResponse.getHasMorePages();

        isLoading = false;
        removeLoadingFooter();
        this.addItems(followees);
        // IF THIS DOESN'T WORK THEN I CAN RETURN THE LIST OF FOLOWEES
    }

    /**
     * Is called when the getFolloweesTask receives an exception and handles said exception.
     * @param exception The exception received from the getFolloweesTask.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        removeLoadingFooter();
        Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
