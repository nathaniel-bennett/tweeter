package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.server.dao.FeedDAO;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.PostUpdateBatch;


public class UpdateFeeds implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent input, Context context) {
        FeedDAO feedDAO = getFeedDAO();
        Serializer serializer = new Serializer();

        for (SQSEvent.SQSMessage msg : input.getRecords()) {
            PostUpdateBatch batch = serializer.deserialize(msg.getBody(), PostUpdateBatch.class);

            feedDAO.addStatusToFeeds(batch.getAliases(), batch.getStatus());
        }

        return null;
    }

    public FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
