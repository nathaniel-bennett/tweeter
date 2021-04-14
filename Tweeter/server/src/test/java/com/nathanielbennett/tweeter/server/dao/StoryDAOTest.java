package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.server.model.StoredStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class StoryDAOTest {

    StoryDAO dao;

    @BeforeEach
    public void setup() {
        dao = new StoryDAO();
    }

    @Test
    public void testGetStorySinglePage() {
        List statuses = dao.getUserStory("dummyUser", 10, null);
        Assertions.assertNotNull(statuses);
        Assertions.assertTrue(statuses.size() > 0);
    }

    @Test
    public void testGetStoryMultiplePage() {
        List<StoredStatus> statuses = dao.getUserStory("dummyUser", 1, null);
        Assertions.assertNotNull(statuses);
        Assertions.assertEquals(1, statuses.size());

        List statuses2 = dao.getUserStory("dummyUser", 1, statuses.get(0).getTimestamp());
        Assertions.assertNotNull(statuses2);
        Assertions.assertEquals(1, statuses2.size());

        Assertions.assertNotEquals(statuses.get(0), statuses2.get(0));
    }

    @Test
    public void testAddToUserStory() {
        String message = UUID.randomUUID().toString();
        StoredStatus status = new StoredStatus("testUser", message, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        dao.addStatusToStory(status);

        List<StoredStatus> statuses = dao.getUserStory("testUser", 15, null);

        boolean foundStatus = false;

        for (StoredStatus storedStatus : statuses) {
            if (storedStatus.getMessage().equals(message)) {
                foundStatus = true;
            }
        }

        Assertions.assertTrue(foundStatus);
    }

}
