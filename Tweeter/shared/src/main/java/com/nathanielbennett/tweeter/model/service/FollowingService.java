package com.nathanielbennett.tweeter.model.service;

import java.io.IOException;

import com.nathanielbennett.tweeter.model.net.TweeterRemoteException;
import com.nathanielbennett.tweeter.model.service.request.FollowRequest;
import com.nathanielbennett.tweeter.model.service.response.FollowResponse;

/**
 * Defines the interface for the 'following' service.
 */
public interface FollowingService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    FollowResponse getFollowees(FollowRequest request)
            throws IOException, TweeterRemoteException;
}
