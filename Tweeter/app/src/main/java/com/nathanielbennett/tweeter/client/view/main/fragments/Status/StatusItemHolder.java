package com.nathanielbennett.tweeter.client.view.main.fragments.Status;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.nathanielbennett.tweeter.R;
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.client.view.main.LoggedInActivity;
import com.nathanielbennett.tweeter.client.view.main.UserActivity;
import com.nathanielbennett.tweeter.client.view.main.fragments.TemplateItemHolder;
import com.nathanielbennett.tweeter.client.view.util.ImageUtils;

public class StatusItemHolder extends TemplateItemHolder<Status> {

    private final ImageView userImage;
    private final TextView userAlias;
    private final TextView userName;
    private final TextView postDateInfo;
    private final TextView statusMessage;
    private User associatedUser;
    private Status associatedStatus;
    private final Context associatedContext;

    /**
     * Creates an instance.
     *
     * @param itemView The view to contain the item.
     */
    public StatusItemHolder(@NonNull View itemView, int viewType, Context context) {
        super(itemView);
        this.associatedContext = context;

        if (viewType == STORY_VIEW_TYPE) {
            userImage = itemView.findViewById(R.id.userImage_Story);
            userAlias = itemView.findViewById(R.id.userAlias_story);
            userName = itemView.findViewById(R.id.userName_story);
            postDateInfo = itemView.findViewById(R.id.postDateInfo);
            statusMessage = itemView.findViewById(R.id.statusMessage);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, UserActivity.class);

                LoggedInActivity activity = (LoggedInActivity) context;

                intent.putExtra("user", associatedUser);
                intent.putExtra(LoggedInActivity.LOGGED_IN_USER_KEY, activity.getLoggedInUser());
                intent.putExtra(LoggedInActivity.AUTH_TOKEN_KEY, activity.getAuthToken());
                context.startActivity(intent);
                activity.finish();
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
        associatedStatus = itemToBind;
        associatedUser = itemToBind.getUserOfStatus();

        userImage.setImageDrawable(ImageUtils.drawableFromByteArray(itemToBind.getUserOfStatus().getImageBytes()));
        userAlias.setText("@" + itemToBind.getUserOfStatus().getAlias());
        userName.setText(itemToBind.getUserOfStatus().getName());
        postDateInfo.setText(itemToBind.getDatePosted());

        SpannableString message = getUserMentions(itemToBind.getStatusMessage());

        statusMessage.setText(message);
        statusMessage.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private SpannableString getUserMentions(String status) {
        SpannableString ss = new SpannableString(status);

        int currentAtSymbol = -1;
        for (int i = 0; i < status.length(); i++) {
            if (status.charAt(i) == '@') {
                currentAtSymbol = i;
            } else if (currentAtSymbol != -1 && (status.charAt(i) == ' ' || i == status.length() - 1)) {
                int start = currentAtSymbol;
                int end = (i == status.length() - 1) ? i + 1 : i;
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {

                        User userForNextActivity = null;

                        for (User user : associatedStatus.getMentions()) {
                            if (user.getAlias().equals(status.substring(start+1, end))) {
                                userForNextActivity = user;
                                break;
                            }
                        }

                        if (userForNextActivity == null) {
                            Toast.makeText(associatedContext, "No user with the given username exists.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent = new Intent(associatedContext, UserActivity.class);

                        LoggedInActivity activity = (LoggedInActivity) associatedContext;

                        intent.putExtra("user", userForNextActivity);
                        intent.putExtra(LoggedInActivity.LOGGED_IN_USER_KEY, activity.getLoggedInUser());
                        intent.putExtra(LoggedInActivity.AUTH_TOKEN_KEY, activity.getAuthToken());

                        associatedContext.startActivity(intent);
                        activity.finish();


                    }
                };
                ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                currentAtSymbol = -1;
            }
        }
        return ss;
    }
}
