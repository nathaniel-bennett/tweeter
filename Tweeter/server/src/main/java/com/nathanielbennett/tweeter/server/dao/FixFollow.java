package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class FixFollow implements RequestHandler<Void, Void> {
    @Override
    public Void handleRequest(Void input, Context context) {
        UserDAO userDAO = new UserDAO();

        LambdaLogger logger = context.getLogger();
        for (int i = 1; i <= 10000; i++) {
            String username = "guy" + Integer.toString(i);

            userDAO.incrementUserFollowing(username);

            if (i % 25 == 0) {
                logger.log("User " + username + "incremented.");
            }
        }

        return null;
    }
}
