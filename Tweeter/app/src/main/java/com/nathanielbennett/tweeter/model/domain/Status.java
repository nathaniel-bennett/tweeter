package com.nathanielbennett.tweeter.model.domain;

import java.util.List;

public class Status {
    private final User userOfStatus;
    private final String statusMessage;
    private final String datePosted;
    private final List<User> mentions;

    /**
     * Creates an instance of status.
     * @param userOfStatus The user who owns the status.
     * @param statusMessage The message.
     * @param datePosted The date posted.
     * @param mentions List of users mentioned in the status.
     */
    public Status (User userOfStatus, String  statusMessage, String datePosted, List<User> mentions) {
        this.userOfStatus = userOfStatus;
        this.statusMessage = statusMessage;
        this.datePosted = datePosted;
        this.mentions = mentions;
    }

    /**
     * Getter for userOfStatus.
     * @return userOfStatus
     */
    public User getUserOfStatus() {
        return userOfStatus;
    }

    /**
     * Getter for statusMessage.
     * @return statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Getter for datePosted.
     * @return datePosted
     */
    public String getDatePosted() {
        return datePosted;
    }

    /**
     * Getter for list of mentions
     * @return mentions
     */
    public List<User> getMentions() {
        return mentions;
    }
}
