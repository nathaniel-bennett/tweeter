package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
import com.nathanielbennett.tweeter.server.DataCache;

import java.io.IOException;

public class FollowDAO {
    /**
     * TODO: document lol
     * @param request
     * @return
     */
    public FollowUserResponse follow(FollowUserRequest request) {
        DataCache cache = DataCache.getInstance();

        User loggedInUser = cache.getUser(request.getUsername());

        User user = cache.getUser(request.getUserToFollow());
        if (user == null) {
            return new FollowUserResponse("Requested user does not exist.");
        }

        cache.getFollowers(user).add(loggedInUser);
        user.setFollowerCount(user.getFollowerCount() + 1);

        cache.getFollowing(loggedInUser).add(user);
        loggedInUser.setFolloweeCount(loggedInUser.getFolloweeCount() + 1);

        return new FollowUserResponse();
    }


    public UnfollowUserResponse unfollow(UnfollowUserRequest request) {
        DataCache cache = DataCache.getInstance();

        User loggedInUser = cache.getUser(request.getUsername());

        User user = cache.getUser(request.getUserToUnfollow());
        if (user == null) {
            return new UnfollowUserResponse("Requested user does not exist.");
        }

        cache.getFollowers(user).add(loggedInUser);
        user.setFollowerCount(user.getFollowerCount() - 1);

        cache.getFollowing(loggedInUser).remove(user);
        loggedInUser.setFolloweeCount(loggedInUser.getFolloweeCount() - 1);

        return new UnfollowUserResponse();
    }

    public CheckFollowingResponse isFollowing(CheckFollowingRequest request) {
        DataCache cache = DataCache.getInstance();

        User loggedInUser = cache.getUser(request.getUsername());

        User user = cache.getUser(request.getOtherUser());
        if (user == null) {
            return new CheckFollowingResponse("Requested user does not exist.");
        }

        if (cache.getFollowing(loggedInUser).contains(user)) {
            return new CheckFollowingResponse(true);
        } else {
            return new CheckFollowingResponse(false);
        }
    }
}
