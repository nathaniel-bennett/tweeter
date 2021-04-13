package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.server.dao.FollowDAO;
import com.nathanielbennett.tweeter.server.model.PostUpdateBatch;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostUpdateFeedMessages implements RequestHandler<SQSEvent, Void> {
    private final int BATCH_SIZE = 25; // NOTE: this is where we can change how many individuals are sent per message
    private final String queueURL = "https://sqs.us-west-2.amazonaws.com/865443059576/CS340TweeterUpdateFeedQueue";

    @Override
    public Void handleRequest(SQSEvent input, Context context) {
        FollowDAO followDAO = getFollowDAO();
        Serializer serializer = new Serializer();

        LambdaLogger logger = context.getLogger();
        logger.log("Started lambda.");

        logger.log("Received " + Integer.toString(input.getRecords().size()) + " SQS messages for this lambda");
        for (SQSEvent.SQSMessage msg : input.getRecords()) {
            StoredStatus status = serializer.deserialize(msg.getBody(), StoredStatus.class);

            List<String> followingAliases = followDAO.getFollowing(status.getAlias());
            if (followingAliases == null) {
                logger.log("followingAliases was null--moving on to next msg");
                continue;
            }
            logger.log("Dispersing status to " + Integer.toString(followingAliases.size()) + " individuals");

            ArrayList<SendMessageBatchRequestEntry> messages = new ArrayList<>();

            for (int i = 0; i < followingAliases.size(); i += BATCH_SIZE) {
                List<String> aliases = followingAliases.subList(i, Integer.min(i + BATCH_SIZE, followingAliases.size()));

                PostUpdateBatch batch = new PostUpdateBatch(status, aliases);

                // Send message to SQS Queue
                String messageBody = new Serializer().serialize(batch);

                SendMessageBatchRequestEntry entry = new SendMessageBatchRequestEntry()
                        .withMessageBody(messageBody)
                        .withId(UUID.randomUUID().toString());

                messages.add(entry);
            }

            for (int i = 0; i < messages.size(); i += 10) {

                logger.log("Sending batch starting at user #" + Integer.toString(i*25));

                SendMessageBatchRequest batchRequest = new SendMessageBatchRequest()
                        .withQueueUrl(queueURL)
                        .withEntries(messages.subList(i, Integer.min(i + 10, messages.size())));

                AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
                SendMessageBatchResult batchResult = sqs.sendMessageBatch(batchRequest);

                logger.log("Message batch sent!");
            }

        }

        logger.log("Finished lambda.");

        return null;
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
