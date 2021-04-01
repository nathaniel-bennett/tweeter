package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.StatusRequest;
import com.nathanielbennett.tweeter.model.service.response.StatusResponse;
import com.nathanielbennett.tweeter.server.DataCache;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class FeedDAO {
    public void addStatus(String alias, Status status) {

    }

    public List<Status> getUserFeed(String alias, int numStatuses, String lastSeenStatus) {
        return null;
    }
}
