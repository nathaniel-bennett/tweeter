package com.nathanielbennett.tweeter.client.view.main.fragments.follow;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.view.main.LoggedInActivity;
import com.nathanielbennett.tweeter.client.view.main.UserActivity;
import com.nathanielbennett.tweeter.client.view.main.fragments.TemplateItemHolder;
import com.nathanielbennett.tweeter.client.view.util.ImageUtils;

public class FollowItemHolder extends TemplateItemHolder<User> {

    private final ImageView userImage;
    private final TextView userAlias;
    private final TextView userName;
    private User associatedUser;

    /**
     * Creates an instance and sets an OnClickListener for the user's row.
     *
     * @param itemView the view on which the user will be displayed.
     * @param context The context needed to make a toast
     */
    public FollowItemHolder(@NonNull View itemView, int viewType, Context context) {
        super(itemView);

        if (viewType == ITEM_VIEW_TYPE) {
            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("user", associatedUser);

                LoggedInActivity activity = (LoggedInActivity) context;

                intent.putExtra(LoggedInActivity.AUTH_TOKEN_KEY, activity.getAuthToken());
                intent.putExtra(LoggedInActivity.LOGGED_IN_USER_KEY, activity.getLoggedInUser());

                activity.startActivity(intent);
                activity.finish();
            });
        } else {
            userImage = null;
            userAlias = null;
            userName = null;
        }
    }

    /**
     * Binds the user's data to the view.
     *
     * @param itemToBind the user.
     */
    @Override
    public void bindItem(User itemToBind) {
        associatedUser = itemToBind;
        userImage.setImageDrawable(ImageUtils.drawableFromByteArray(itemToBind.imageToBytes()));
        userAlias.setText(itemToBind.getAlias());
        userName.setText(itemToBind.getName());
    }
}
