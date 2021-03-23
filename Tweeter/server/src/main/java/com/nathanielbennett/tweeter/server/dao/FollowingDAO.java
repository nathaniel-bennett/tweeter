package com.nathanielbennett.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.CheckFollowingRequest;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.CheckFollowingResponse;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;
import com.nathanielbennett.tweeter.server.DataCache;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowingDAO {
    private static final DataCache dc = DataCache.getInstance();

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowResponse getFollowing(FollowRequest request) {

        User loggedInUser = dc.getUser(request.getFollowAlias());
        if (loggedInUser == null) {
            throw new BadRequestException("User was not found in database.");
        }

        List<User> following = dc.getFollowing(loggedInUser);
        if (following == null) {
            return new FollowResponse(new ArrayList<User>(), false);
        }


        List<User> allFollowing = getFollowingFromUserName(request.getFollowAlias());
        List<User> responseFollowing = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFollowStartingIndex(request.getLastFollowAlias(), allFollowing);

            for(int limitCounter = 0; followeesIndex < allFollowing.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowing.add(allFollowing.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowing.size();
        }

        return new FollowResponse(responseFollowing, hasMorePages);
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getFollowingFromUserName(String username) {
        User user = dc.getUser(username);
        return dc.getFollowing(user);
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
