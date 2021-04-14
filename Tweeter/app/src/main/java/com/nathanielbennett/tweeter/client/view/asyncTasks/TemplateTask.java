package com.nathanielbennett.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;
import com.nathanielbennett.tweeter.model.service.response.TweeterAPIResponse;
import com.nathanielbennett.tweeter.client.util.ByteArrayUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TemplateTask extends AsyncTask<TweeterAPIRequest,  Void, TweeterAPIResponse> {

    private Exception exception;

    public interface Observer {

    }


    protected abstract TweeterAPIResponse performTask(TweeterAPIRequest request) throws IOException;

    protected abstract void taskSuccessful(TweeterAPIResponse response);

    protected abstract void taskUnsuccessful(TweeterAPIResponse response);

    protected abstract void handleException(Exception ex);



    /**
     * The method that is invoked on a background thread to log the user in. This method is
     * invoked indirectly by calling {@link #execute(TweeterAPIRequest...)}.
     *
     * @param requests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected TweeterAPIResponse doInBackground(TweeterAPIRequest... requests) {
        TweeterAPIResponse response = null;

        try {
            response = performTask(requests[0]);

        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }


    /**
     * Loads the profile image data for each user in the list of users.
     *
     * @param statuses the users whose profile images are to be loaded.
     */
    protected void loadStatusImages(List<com.nathanielbennett.tweeter.model.domain.Status> statuses) throws IOException {
        Map<String, byte[]> images = new HashMap<>();

        for(com.nathanielbennett.tweeter.model.domain.Status status : statuses) {
            String url = status.getUserOfStatus().getImageUrl();
            if (images.containsKey(url)) {
                status.getUserOfStatus().setImageBytes(images.get(url));
            } else {
                loadUserImage(status.getUserOfStatus());
                images.put(url, status.getUserOfStatus().getImageBytes());
            }
        }
    }

    /**
     * Loads the profile image data for each user in the list of users.
     *
     * @param users the users whose profile images are to be loaded.
     */
    protected void loadUserImages(List<User> users) throws IOException {
        for(User user : users) {
            loadUserImage(user);
        }
    }

    /**
     * Loads the profile image for the user.
     *
     * @param user the user whose profile image is to be loaded.
     */

    protected void loadUserImage(User user) {
        try {
            if (user.getImageBytes() != null) {
                return;
            }
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString(), e);
        }
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(TweeterAPIRequest...)} method) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(TweeterAPIResponse response) {
        if(exception != null) {
            handleException(exception);
        } else if(response.getSuccess()) {
            taskSuccessful(response);
        } else {
            taskUnsuccessful(response);
        }
    }
}
