package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.server.DataCache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostDAO {
    private static final DataCache dc = DataCache.getInstance();

    public PostResponse addToStory(PostRequest request) {

        if (dc.getUser(request.getUsername()) == null) {
            throw new AssertionError("User not found in database");
        }

        Date date = Calendar.getInstance().getTime();

        Status newStatus = new Status(dc.getUser(request.getUsername()), request.getStatus(), date.toString(),
                getMentions(request.getStatus()));

        dc.postStatus(dc.getUser(request.getUsername()), newStatus);

        return new PostResponse();
    }

    private List<User> getMentions(String message) {
        List<User> mentions = new ArrayList<>();
        int atSymbol = -1;
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '@') {
                atSymbol = i;
            } else if ((message.charAt(i) == ' ' || i == message.length() - 1) && atSymbol != -1) {
                int end = (i == message.length() - 1) ? i + 1 : i;
                String username = message.substring(atSymbol, end);
                User mentioned = dc.getUser(username);
                if (mentioned != null) {
                    mentions.add(mentioned);
                }
            }
        }
        return mentions;
    }
}
