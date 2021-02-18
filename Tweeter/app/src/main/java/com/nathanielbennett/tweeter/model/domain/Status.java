package com.nathanielbennett.tweeter.model.domain;

import java.util.List;

public class Status {
    private String statusMessage;
    private String datePosted;
    private List<User> mentions;

    // TODO: DISCUSS HOW TO HANDLE MENTIONS (@AWESOMEGROUP) AND URLS (WWW.AWESOMEPEOPLE.COM)

    public Status (String statusMessage, String datePosted, List<User> mentions) {
        this.statusMessage = statusMessage;
        this.datePosted = datePosted;
        this.mentions = mentions;
    }




}
