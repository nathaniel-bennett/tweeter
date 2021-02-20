package com.nathanielbennett.tweeter.view.main.fragments.Status;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.view.main.UserActivity;
import com.nathanielbennett.tweeter.view.main.fragments.TemplateItemHolder;
import com.nathanielbennett.tweeter.view.util.ImageUtils;

public class StatusItemHolder extends TemplateItemHolder<Status> {

    private final ImageView userImage;
    private final TextView userAlias;
    private final TextView userName;
    private final TextView postDateInfo;
    private final TextView statusMessage;
    private User associatedUser;

    /**
     * Creates an instance.
     *
     * @param itemView
     */
    public StatusItemHolder(@NonNull View itemView, int viewType, Context context) {
        super(itemView);

        if (viewType == STORY_VIEW_TYPE) {
            userImage = itemView.findViewById(R.id.userImage_Story);
            userAlias = itemView.findViewById(R.id.userAlias_story);
            userName = itemView.findViewById(R.id.userName_story);
            postDateInfo = itemView.findViewById(R.id.postDateInfo);
            statusMessage = itemView.findViewById(R.id.statusMessage);

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
            postDateInfo = null;
            statusMessage = null;
        }
    }

    @Override
    public void bindItem(Status itemToBind) {
        associatedUser = itemToBind.getUserOfStatus();
        try {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(itemToBind.getUserOfStatus().getImageBytes()));
            userAlias.setText(itemToBind.getUserOfStatus().getAlias());
            userName.setText(itemToBind.getUserOfStatus().getName());
            postDateInfo.setText(itemToBind.getDatePosted());
            statusMessage.setText(itemToBind.getStatusMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
