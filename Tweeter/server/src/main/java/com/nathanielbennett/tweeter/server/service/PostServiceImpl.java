package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.model.service.PostService;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.server.dao.PostDAO;

public class PostServiceImpl implements PostService {

    @Override
    public PostResponse addPost(PostRequest request) {
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
