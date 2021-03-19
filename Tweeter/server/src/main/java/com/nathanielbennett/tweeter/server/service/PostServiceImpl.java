package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.PostService;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;
import com.nathanielbennett.tweeter.server.dao.PostDAO;

public class PostServiceImpl implements PostService {

    @Override
    public PostResponse addPost(PostRequest request) {
        if (request == null) {
            throw new BadRequestException("Message body missing or malformed");
        }

        if (request.getAuthToken() == null || request.getAuthToken().getTokenID().isEmpty()) {
            throw new NotAuthorizedException("Authorization Token not included in request header");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Message body missing user's username");
        }

        return null;
    }

    /**
     * Returns an instance of {@link PostDAO}. Allows mocking of the PostDAO class
     * for testing purposes. All usages of PostDAO should get their PostDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public PostDAO getPostDAO() {
        return new PostDAO();
    }
}
