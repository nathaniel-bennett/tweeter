package com.nathanielbennett.tweeter.view.main.fragments.follow;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.view.main.MainActivity;
import com.nathanielbennett.tweeter.view.main.UserActivity;
import com.nathanielbennett.tweeter.view.main.fragments.TemplateItemHolder;
import com.nathanielbennett.tweeter.view.util.ImageUtils;

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

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("user", associatedUser);
                    context.startActivity(intent);
                }
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
        userImage.setImageDrawable(ImageUtils.drawableFromByteArray(itemToBind.getImageBytes()));
        userAlias.setText(itemToBind.getAlias());
        userName.setText(itemToBind.getName());
    }
}
