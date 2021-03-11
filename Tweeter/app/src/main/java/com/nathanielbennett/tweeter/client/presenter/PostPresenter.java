package com.nathanielbennett.tweeter.client.presenter;

import com.nathanielbennett.tweeter.client.model.service.PostServiceProxy;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;

import java.io.IOException;

public class PostPresenter implements TemplatePresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates.
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which the class is the presenter.
     */
    public PostPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a request to the story service for the specified data.
     *
     * @param request the request to be made to the service.
     * @return the response from the service.
     * @throws IOException if an error occurred in sending/receiving the action.
     */
    public PostResponse post(PostRequest request) throws IOException {
        PostServiceProxy postServiceProxy = getPostService();
        return postServiceProxy.addPost(request);
    }

    /**
     * returns an instance of {@link PostServiceProxy}. Allows mocking of the PostService class for
     * testing purposes. All usages of PostService should get their PostService instance from this
     * method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    protected PostServiceProxy getPostService() {
        return new PostServiceProxy();
    }
}
