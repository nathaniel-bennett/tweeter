package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowUserRequest;
import com.nathanielbennett.tweeter.model.service.request.UnfollowUserRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowUserResponse;
import com.nathanielbennett.tweeter.model.service.response.UnfollowUserResponse;
import com.nathanielbennett.tweeter.server.DataCache;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.UserAlreadyFollowedException;
import com.nathanielbennett.tweeter.server.exceptions.UserAlreadyUnfollowedException;

import java.io.IOException;
import java.util.ArrayList;

public class FollowDAO {
    /**
     * TODO: document lol
     * @param request
     * @return
     */
    public FollowUserResponse follow(FollowUserRequest request) {
        DataCache cache = DataCache.getInstance();

        User loggedInUser = cache.getUser(request.getUsername());
        if (loggedInUser == null) {
            throw new BadRequestException("The user specified in the request does not exist.");
        }

        User user = cache.getUser(request.getUserToFollow());
        if (user == null) {
            throw new BadRequestException("Requested user to follow does not exist.");
        }

        ArrayList<User> followers = cache.getFollowers(loggedInUser);
        if (followers.contains(loggedInUser)) {
            throw new UserAlreadyFollowedException("Requested user to follow is already followed.");
        }

        followers.add(loggedInUser);
        user.setFollowerCount(user.getFollowerCount() + 1);

        cache.getFollowing(loggedInUser).add(user);
        loggedInUser.setFolloweeCount(loggedInUser.getFolloweeCount() + 1);

        return new FollowUserResponse();
    }


    public UnfollowUserResponse unfollow(UnfollowUserRequest request) {
        DataCache cache = DataCache.getInstance();

        User loggedInUser = cache.getUser(request.getUsername());
        if (loggedInUser == null) {
            throw new BadRequestException("The user specified in the request does not exist.");
        }

        User user = cache.getUser(request.getUserToUnfollow());
        if (user == null) {
            throw new BadRequestException("Requested user to unfollow does not exist.");
        }

        ArrayList<User> followers = cache.getFollowers(loggedInUser);
        if (!followers.contains(loggedInUser)) {
            throw new UserAlreadyUnfollowedException("Requested user to unfollow was not being followed in the first place.");
        }
        followers.remove(loggedInUser);

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
            throw new BadRequestException("Requested user does not exist.");
        }

        if (cache.getFollowing(loggedInUser).contains(user)) {
            return new CheckFollowingResponse(true);
        } else {
            return new CheckFollowingResponse(false);
        }
    }
}
