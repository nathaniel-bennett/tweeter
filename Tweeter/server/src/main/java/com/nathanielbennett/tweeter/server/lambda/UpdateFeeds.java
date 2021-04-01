package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.server.dao.FeedDAO;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessFailureException;
import com.nathanielbennett.tweeter.server.model.PostUpdateBatch;


public class UpdateFeeds implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent input, Context context) {
        FeedDAO feedDAO = getFeedDAO();
        Serializer serializer = new Serializer();

        for (SQSEvent.SQSMessage msg : input.getRecords()) {
            PostUpdateBatch batch = serializer.deserialize(msg.getBody(), PostUpdateBatch.class);

            for (String alias : batch.getAliases()) {
                try {
                    feedDAO.addStatus(alias, batch.getStatus()); // TODO: you need to do these all in one database commit!

                } catch (DataAccessFailureException e) {
                    // TODO: Log data access failure here
                }
            }
        }

        return null;
    }

    public FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
