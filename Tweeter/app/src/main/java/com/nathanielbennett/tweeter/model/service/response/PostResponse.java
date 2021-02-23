package com.nathanielbennett.tweeter.model.service.response;

public class PostResponse extends TweeterAPIResponse{
    public PostResponse(Boolean success, String message){
        this.success = success;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }
}
