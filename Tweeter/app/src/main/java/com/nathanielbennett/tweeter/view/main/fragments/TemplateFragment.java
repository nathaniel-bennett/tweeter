package com.nathanielbennett.tweeter.view.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.presenter.TemplatePresenter;

import org.jetbrains.annotations.NotNull;

abstract public class TemplateFragment<Item> extends Fragment {

    public static String USER_KEY = "UserKey";
    public static String AUTH_TOKEN_KEY = "AuthTokenKey";

    public TemplateRecyclerViewAdapter recyclerViewAdapter;
    public TemplatePresenter presenter;

    public User user;
    public AuthToken authToken;


    //TODO: Are JavaDocs necessary for These kind of methods?
    @Nullable
    @Override
    abstract public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * Generates a new RecyclerViewPaginationScrollListener.
     * @param layoutManager The layout manager associated with the Scroll Listener.
     * @return a new RecyclerViewPaginationScrollListener.
     */
    public RecyclerViewPaginationScrollListener getOnScrollListener(LinearLayoutManager layoutManager) {
        return new RecyclerViewPaginationScrollListener(layoutManager);
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    public class RecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        RecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!recyclerViewAdapter.isLoading && recyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    recyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }

}
