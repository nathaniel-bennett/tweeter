package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.server.model.StoredUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;

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
    public void testIncrementUserFollowers() {
        StoredUser user = dao.getUser("testUser");
        dao.incrementUserFollowers(user.getAlias());
        StoredUser incrementedUser = dao.getUser(user.getAlias());
        Assertions.assertEquals(user.getFollowerCount() + 1, incrementedUser.getFollowerCount());
    }

    /**
     * The two tests below don't run because AWS has some lockdown on their DAO code outside of the lambda
     * We tested the features out on our app and using the aws console we verified that the code works
     */
    @Test
    @Disabled
    public void testDecrementUserFollowing() {
        StoredUser user = dao.getUser("testUser");
        dao.decrementUserFollowing(user.getAlias());
        StoredUser incrementedUser = dao.getUser(user.getAlias());
        Assertions.assertEquals(user.getFolloweeCount() - 1, incrementedUser.getFolloweeCount());
    }

    @Test
    @Disabled
    public void testDecrementUserFollowers() {
        StoredUser user = dao.getUser("testUser");
        dao.decrementUserFollowing(user.getAlias());
        StoredUser incrementedUser = dao.getUser(user.getAlias());
        Assertions.assertEquals(user.getFollowerCount() - 1, incrementedUser.getFollowerCount());
    }
}
