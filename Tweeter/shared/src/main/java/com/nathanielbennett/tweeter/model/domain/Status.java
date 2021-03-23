package com.nathanielbennett.tweeter.model.domain;

import java.util.List;

public class Status {
    private User userOfStatus;
    private String statusMessage;
    private String datePosted;
    private List<User> mentions;

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

    public Status() { }

    /**
     * Getter for userOfStatus.
     * @return userOfStatus
     */
    public User getUserOfStatus() {
        return userOfStatus;
    }

    public void setUserOfStatus(User user) {
        this.userOfStatus = user;
    }

    /**
     * Getter for statusMessage.
     * @return statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String message) {
        this.statusMessage = message;
    }

    /**
     * Getter for datePosted.
     * @return datePosted
     */
    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String date) {
        this.datePosted = date;
    }

    /**
     * Getter for list of mentions
     * @return mentions
     */
    public List<User> getMentions() {
        return mentions;
    }

    public void setMentions(List<User> mentions) {
        this.mentions = mentions;
    }
}
