package com.nathanielbennett.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.server.dao.FollowDAO;
import com.nathanielbennett.tweeter.server.model.PostUpdateBatch;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import java.util.List;

public class PostUpdateFeedMessages implements RequestHandler<SQSEvent, Void> {
    static final int BATCH_SIZE = 25; // NOTE: this is where we can change how many individuals are sent per message


    @Override
    public Void handleRequest(SQSEvent input, Context context) {
        FollowDAO followDAO = getFollowDAO();
        Serializer serializer = new Serializer();

        for (SQSEvent.SQSMessage msg : input.getRecords()) {
            StoredStatus status = serializer.deserialize(msg.getBody(), StoredStatus.class);

            List<String> followingAliases = followDAO.getFollowing(status.getAlias(), 5, null);

            for (int i = 0; i < followingAliases.size(); i += BATCH_SIZE) {
                List<String> aliases = followingAliases.subList(i, Integer.min(i + 25, followingAliases.size()));

                PostUpdateBatch batch = new PostUpdateBatch(status, aliases);

                // Send message to SQS Queue
                String messageBody = new Serializer().serialize(batch);
                String queueURL = "https://sqs.us-east-1.amazonaws.com/594032838338/340queue"; // TODO: change this to second queue

                SendMessageRequest sendMessageRequest = new SendMessageRequest()
                        .withQueueUrl(queueURL)
                        .withMessageBody(messageBody);

                AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
                SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);

                // TODO: could log message ID here

            }

        }

        return null;
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
