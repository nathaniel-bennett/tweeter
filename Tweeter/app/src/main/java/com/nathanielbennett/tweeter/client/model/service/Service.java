package com.nathanielbennett.tweeter.client.model.service;

import com.nathanielbennett.tweeter.client.model.net.ServerFacade;

public abstract class Service {


    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    protected ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
