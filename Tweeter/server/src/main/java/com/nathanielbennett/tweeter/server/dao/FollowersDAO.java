package com.nathanielbennett.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.server.DataCache;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;


/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowersDAO {
    private static final DataCache dc = DataCache.getInstance();
    /**
     * Returns the users the the user specified in the request is being followed by. Uses information
     * in the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation returns
     * generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers response
     */
    public FollowResponse getFollowers(FollowRequest request) {
        User loggedInUser = dc.getUser(request.getFollowAlias());
        if (loggedInUser == null) {
            throw new BadRequestException("User was not found in database.");
        }

        List<User> following = dc.getFollowing(loggedInUser);
        if (following == null) {
            return new FollowResponse(new ArrayList<User>(), false);
        }



        List<User> allFollowers = getFollowersByUserName(request.getFollowAlias());
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        if (allFollowers == null) {
            throw new BadRequestException("The requested user does not exist in the database.");
        }

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followersIndex = getFollowStartingIndex(request.getLastFollowAlias(), allFollowers);

            for (int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowResponse(responseFollowers, hasMorePages);
    }

    /**
     * Returns the list of dummy follower data. This is written as a separate method to allow
     * mocking of the followers.
     *
     * @return the followers.
     */
    List<User> getFollowersByUserName(String username) {
        User user = dc.getUser(username);
        return dc.getFollowers(user);
    }

    /**
     * Determines the index for the first followUser in the specified 'allFollow' list that should
     * be returned in the current request. This will be the index of the next followUser after the
     * specified 'lastFollow'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollow the generated list of followUser from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFollowStartingIndex(String lastFolloweeAlias, List<User> allFollow) {
        int followeesIndex = 0;
        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollow.size(); i++) {
                if(lastFolloweeAlias.equals(allFollow.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }
        return followeesIndex;
    }
}
