package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.server.model.StoredStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FeedDaoTest {

    FeedDAO feedDAO;

    @BeforeEach
    public void setup() {
        feedDAO = new FeedDAO();
    }

    @Test
    public void testGetFeedPage() {
        List<StoredStatus> statuses = feedDAO.getUserFeed("testUser", 10, null);
        Assertions.assertNotNull(statuses);
        Assertions.assertEquals(2, statuses.size());
    }

    @Test
    public void testGetFeedMultiplePages() {
        List<StoredStatus> statuses = feedDAO.getUserFeed("testUser", 1, null);
        Assertions.assertNotNull(statuses);
        Assertions.assertEquals(1, statuses.size());

        List<StoredStatus> statuses2 = feedDAO.getUserFeed("testUser", 1, statuses.get(0).getTimestamp());
        Assertions.assertNotNull(statuses2);
        Assertions.assertEquals(1, statuses2.size());
        Assertions.assertNotEquals(statuses.get(0), statuses2.get(0));

    }

    @Test
    public void testGetFeedOfNonexistentUser() {
        List<StoredStatus> statuses = feedDAO.getUserFeed("I_DONT_EXIST", 10, null);
        Assertions.assertNull(statuses);
    }

    @Test
    public void testAddStatusToFeeds() {
        List<String> owners = Arrays.asList("testUser");
        String message = UUID.randomUUID().toString();
        StoredStatus status = new StoredStatus("dummyUser", message, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        feedDAO.addStatusToFeeds(owners, status);
        List<StoredStatus> statuses = feedDAO.getUserFeed("testUser", 15, null);

        boolean foundStatus = false;

        for (StoredStatus storedStatus : statuses) {
            if (storedStatus.getMessage().equals(message)) {
                foundStatus = true;
            }
        }

        Assertions.assertTrue(foundStatus);
    }
}
