package com.nathanielbennett.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.service.request.Request;
import com.nathanielbennett.tweeter.model.service.response.Response;
import com.nathanielbennett.tweeter.util.ByteArrayUtils;

import java.io.IOException;

public abstract class TemplateTask extends AsyncTask<Request,  Void, Response> {

    private Exception exception;


    protected abstract Response performTask(Request request) throws IOException;

    protected abstract void taskSuccessful(Response response);

    protected abstract void taskUnsuccessful(Response response);

    protected abstract void handleException(Exception ex);



    /**
     * The method that is invoked on a background thread to log the user in. This method is
     * invoked indirectly by calling {@link #execute(Request...)}.
     *
     * @param requests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected Response doInBackground(Request... requests) {
        Response response = null;

        try {

            response = performTask(requests[0]);

            /*
            if(response.isSuccess()) {
                loadImage(response.getUser());
            }
            */
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Loads the profile image for the user.
     *
     * @param user the user whose profile image is to be loaded.
     */

    protected void loadImage(User user) {
        try {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString(), e);
        }
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(Request...)} method) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(Response response) {
        if(exception != null) {
            handleException(exception);
        } else if(response.isSuccess()) {
            taskSuccessful(response);
        } else {
            taskUnsuccessful(response);
        }
    }
}
