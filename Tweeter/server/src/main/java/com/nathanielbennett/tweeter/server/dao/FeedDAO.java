package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import java.util.List;

public class FeedDAO {
    public void addStatus(String alias, StoredStatus status) {

    }

    public void addStatus(List<String> aliases, StoredStatus status) {

    }

    public List<StoredStatus> getUserFeed(String alias, int numStatuses, String lastSeenStatus) {
        return null;
    }
}
