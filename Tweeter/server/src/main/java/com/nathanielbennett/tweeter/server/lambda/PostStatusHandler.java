package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.server.service.PostServiceImpl;

public class PostStatusHandler implements RequestHandler<PostRequest, PostResponse> {
    @Override
    public PostResponse handleRequest(PostRequest request, Context context) {
        PostServiceImpl postService = new PostServiceImpl();
        return postService.addPost(request);
    }
}
