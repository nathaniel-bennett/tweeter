package com.nathanielbennett.tweeter.model.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nathanielbennett.tweeter.model.service.request.TweeterAPIRequest;

public class Serializer {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String serialize(TweeterAPIRequest request) {
        return gson.toJson(request);
    }

    public <T> T deserialize(String response, Class<T> type) {
        return type.cast(gson.fromJson(response, type));
    }
}
