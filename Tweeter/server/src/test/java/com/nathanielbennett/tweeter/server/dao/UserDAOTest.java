package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.server.model.StoredUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDAOTest {

    UserDAO dao;

    @BeforeEach
    public void setup() {
        dao = new UserDAO();
    }

    @Test
    public void testGetUser() {
        StoredUser user = dao.getUser("testUser");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("testUser", user.getAlias());
    }

    @Test
    public void testIncrementUserFollowing() {
        StoredUser user = dao.getUser("testUser");
        dao.incrementUserFollowing(user.getAlias());
        StoredUser incrementedUser = dao.getUser(user.getAlias());
        Assertions.assertEquals(user.getFolloweeCount() + 1, incrementedUser.getFolloweeCount());
    }

    @Test
    public void testDecrementUserFollowing() {
        StoredUser user = dao.getUser("testUser");
        dao.decrementUserFollowing(user.getAlias());
        StoredUser incrementedUser = dao.getUser(user.getAlias());
        Assertions.assertEquals(user.getFolloweeCount() - 1, incrementedUser.getFolloweeCount());
    }

    @Test
    public void testIncrementUserFollowers() {
        StoredUser user = dao.getUser("testUser");
        dao.incrementUserFollowers(user.getAlias());
        StoredUser incrementedUser = dao.getUser(user.getAlias());
        Assertions.assertEquals(user.getFollowerCount() + 1, incrementedUser.getFollowerCount());
    }

    @Test
    public void testDecrementUserFollowers() {
        StoredUser user = dao.getUser("testUser");
        dao.decrementUserFollowing(user.getAlias());
        StoredUser incrementedUser = dao.getUser(user.getAlias());
        Assertions.assertEquals(user.getFollowerCount() - 1, incrementedUser.getFollowerCount());
    }
}
