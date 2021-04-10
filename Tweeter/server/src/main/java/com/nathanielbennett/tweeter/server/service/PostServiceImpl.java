package com.nathanielbennett.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.AuthorizationService;
import com.nathanielbennett.tweeter.model.service.PostService;
import com.nathanielbennett.tweeter.model.service.request.AuthorizationRequest;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.AuthorizationResponse;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.server.dao.StoryDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostServiceImpl implements PostService {

    @Override
    public PostResponse addPost(PostRequest request) {
        if (request == null) {
            throw new BadRequestException("Message body missing or malformed");
        }

        if (request.getAuthToken() == null || request.getAuthToken().getAuthTokenID().isEmpty()) {
            throw new NotAuthorizedException("Authorization Token not included in request header");
        }

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BadRequestException("Message body missing user's username");
        }

        // Now check to make sure username/auth token combo is valid

        AuthorizationRequest authRequest = new AuthorizationRequest(request);
        AuthorizationService authService = new AuthorizationServiceImpl();
        AuthorizationResponse authResponse = authService.isAuthorized(authRequest);

        if (!authResponse.getSuccess()) {
            return new PostResponse(authResponse.getErrorMessage());
        }

        StoredStatus status = new StoredStatus(request.getUsername(), request.getStatus(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        // Post the status to the user's own feed (*hopefully* within 1000 ms)
        getStoryDAO().addStatusToStory(status);

        // Send message to SQS Queue
        String messageBody = new Serializer().serialize(status);
        String queueURL = "https://sqs.us-west-2.amazonaws.com/865443059576/CS340TweeterPostStatusQueue";

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueURL)
                .withMessageBody(messageBody);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);

        // NOTE: could log message ID/result here or something

        return new PostResponse();
    }

    /**
     * Returns an instance of {@link StoryDAO}. Allows mocking of the PostDAO class
     * for testing purposes. All usages of PostDAO should get their PostDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public StoryDAO getStoryDAO() {
        return new StoryDAO();
    }
}
