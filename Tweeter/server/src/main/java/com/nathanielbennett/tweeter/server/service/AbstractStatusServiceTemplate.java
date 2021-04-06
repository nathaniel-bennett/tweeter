package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.StoredStatus;
import com.nathanielbennett.tweeter.server.model.StoredUser;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStatusServiceTemplate {

    protected List<Status> formUserStatuses(List<StoredStatus> storedStatuses) {
        List<Status> userStatuses = new ArrayList<>();

        for (StoredStatus storedStatus : storedStatuses) {
            Status status = formUserStatus(storedStatus);
            userStatuses.add(status);
        }

        return userStatuses;
    }


    protected Status formUserStatus(StoredStatus storedStatus) {
        UserDAO userDAO = getUserDAO();

        StoredUser storedUser = userDAO.getUser(storedStatus.getAlias());
        if (storedUser == null) {
            throw new DataAccessException("User not found within database.");
        }

        User statusOwner = storedUser.toUser();
        List<User> mentionedUsers = getMentionedUsers(storedStatus.getMessage());

        return new Status(statusOwner, storedStatus.getMessage(), storedStatus.getTimestamp(), mentionedUsers);
    }

    protected List<User> getMentionedUsers(String status) {
        List<String> mentionedAliases = getMentionedAliases(status);
        List<User> mentionedUsers = new ArrayList<>();

        UserDAO userDAO = getUserDAO();

        for (String alias : mentionedAliases) {
            StoredUser storedUser = userDAO.getUser(alias);
            if (storedUser != null) { // TODO: Make sure that this returns null instead of throwing error when no user is to be found. Either that or make UserNotFound exception...
                mentionedUsers.add(storedUser.toUser());
            }
        }

        return mentionedUsers;
    }

    protected List<String> getMentionedAliases(String status) {
        StringBuilder sb = new StringBuilder(status);
        List<String> userMentions = new ArrayList<>();

        Integer start = null;

        for (int i = 0; i < status.length(); i++) {
            if (sb.charAt(i) == '@') {
                start = i;
                continue;
            }

            if (start != null) {
                if (!(Character.isAlphabetic(status.charAt(i)) || Character.isDigit(status.charAt(i)))
                        || i == status.length() - 1) {
                    int end = (i == status.length() - 1) ? i + 1 : i;

                    userMentions.add(sb.substring(start, end)); // TODO: make sure this substring works
                    start = null;
                }
            }
        }

        return userMentions;
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }
}
