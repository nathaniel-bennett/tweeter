package com.nathanielbennett.tweeter.client.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.view.main.fragments.Status.Story.StoryFragment;
import com.nathanielbennett.tweeter.client.view.main.fragments.follow.followers.FollowersFragment;
import com.nathanielbennett.tweeter.client.view.main.fragments.follow.following.FollowingFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages
 * of the Main Activity.
 */
class UserSectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int STORY_FRAGMENT_POSITION = 0;
    private static final int FOLLOWING_FRAGMENT_POSITION = 1;
    private static final int FOLLOWER_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;
    private final User user;
    private final AuthToken authToken;

    public UserSectionsPagerAdapter(Context context, FragmentManager fm, User user, AuthToken authToken) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        this.user = user;
        this.authToken = authToken;
    }

    @Override
    public @NotNull Fragment getItem(int position) {
        if (position == FOLLOWING_FRAGMENT_POSITION) {
            return FollowingFragment.newInstance(user, authToken);
        } else if (position == FOLLOWER_FRAGMENT_POSITION) {
            return FollowersFragment.newInstance(user, authToken);
        } else if (position == STORY_FRAGMENT_POSITION) {
            return StoryFragment.newInstance(user, authToken);
        } else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}