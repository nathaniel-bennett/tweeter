package com.nathanielbennett.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.model.net.Serializer;
import com.nathanielbennett.tweeter.model.service.PostService;
import com.nathanielbennett.tweeter.model.service.request.PostRequest;
import com.nathanielbennett.tweeter.model.service.response.PostResponse;
import com.nathanielbennett.tweeter.server.dao.StoryDAO;
import com.nathanielbennett.tweeter.server.dao.UserDAO;
import com.nathanielbennett.tweeter.server.exceptions.BadRequestException;
import com.nathanielbennett.tweeter.server.exceptions.NotAuthorizedException;
import com.nathanielbennett.tweeter.server.dao.PostDAO;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        UserDAO userDAO = getUserDAO();
        User associatedUser = userDAO.getUser(request.getUsername());
        List<User> mentionedUsers = getMentionedUsers(request.getStatus());

        Status status = new Status(associatedUser, request.getStatus(), DateFormat.getDateTimeInstance().format(new Date()), mentionedUsers);

        getStoryDAO().addStatus(status);

        // Send message to SQS Queue
        String messageBody = new Serializer().serialize(status);
        String queueURL = "https://sqs.us-east-1.amazonaws.com/594032838338/340queue"; // TODO: change this to first queue

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueURL)
                .withMessageBody(messageBody);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);

        // TODO: could log message ID here

        PostResponse response = new PostResponse();
        response.setSuccess(true);

        return response;
    }

    /**
     * Returns an instance of {@link PostDAO}. Allows mocking of the PostDAO class
     * for testing purposes. All usages of PostDAO should get their PostDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public StoryDAO getStoryDAO() {
        return new StoryDAO();
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }


    private List<User> getMentionedUsers(String status) {
        List<String> mentionedAliases = getMentionedAliases(status);
        List<User> mentionedUsers = new ArrayList<>();

        UserDAO userDAO = getUserDAO();

        for (String alias : mentionedAliases) {
            User user = userDAO.getUser(alias);
            if (user != null) { // TODO: Make sure that this returns null instead of throwing error when no user is to be found. Either that or make UserNotFound exception...
                mentionedUsers.add(user);
            }
        }

        return mentionedUsers;
    }

    private List<String> getMentionedAliases(String status) {
        StringBuilder sb = new StringBuilder(status);
        List<String> userMentions = new ArrayList<>();

        Integer start = null;

        for (int i = 0; i < status.length(); i++) {
            if (sb.charAt(i) == '@') {
                start = i;
                continue;
            }

            if (start != null) {
                if (!(Character.isAlphabetic(status.charAt(i)) || Character.isDigit(status.charAt(i)))
                            || i == status.length() - 1) {
                    int end = (i == status.length() - 1) ? i + 1 : i;

                    userMentions.add(sb.substring(start, end)); // TODO: make sure this substring works
                    start = null;
                }
            }
        }

        return userMentions;
    }
}
