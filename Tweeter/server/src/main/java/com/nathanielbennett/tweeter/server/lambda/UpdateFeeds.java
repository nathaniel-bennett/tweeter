package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
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

        LambdaLogger logger = context.getLogger();

        logger.log("Started lambda.");

        logger.log("Lambda processing " + Integer.toString(input.getRecords().size()) + " records");
        for (SQSEvent.SQSMessage msg : input.getRecords()) {
            logger.log("Processing next SQS msg.");

            PostUpdateBatch batch = serializer.deserialize(msg.getBody(), PostUpdateBatch.class);
            if (batch.getAliases() == null || batch.getAliases().size() == 0) {
                logger.log("ERROR: No aliases found in batch");
                continue;
            }

            if (batch.getStatus() == null) {
                logger.log("ERROR: Status was null");
                continue;
            }

            if (batch.getStatus().getAlias() == null || batch.getStatus().getAlias().isEmpty()) {
                logger.log("ERROR: status alias was empty");
                continue;
            }

            if (batch.getStatus().getAlias() == null || batch.getStatus().getAlias().isEmpty()) {
                logger.log("ERROR: status alias was empty");
                continue;
            }

            if (batch.getStatus().getMessage() == null) {
                logger.log("ERROR: status message was empty");
                continue;
            }

            if (batch.getStatus().getTimestamp() == null) {
                logger.log("ERROR: status timestamp was empty");
            }

            feedDAO.addStatusToFeeds(batch.getAliases(), batch.getStatus());
        }

        logger.log("finished lambda.");
        return null;
    }

    public FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
