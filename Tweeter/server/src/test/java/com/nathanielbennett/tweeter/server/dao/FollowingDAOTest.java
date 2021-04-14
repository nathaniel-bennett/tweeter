package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.server.model.StoredFollowRelationship;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FollowingDAOTest {

    FollowDAO dao;

    @BeforeEach
    public void setup() {
        dao = new FollowDAO();
    }

    @Test
    public void testIsFollowing_validRelationship() {
        Assertions.assertTrue(dao.isFollowing("guy1", "dummyUser"));
    }

    @Test
    public void testIsFollowing_invalidRelationship() {
        Assertions.assertFalse(dao.isFollowing("guy1", "guy2"));
    }

    @Test
    public void testAddFollowRelationship() {
        dao.addFollowRelationship("guy1", "guy3");
        Assertions.assertTrue(dao.isFollowing("guy1", "guy3"));
    }

    @Test
    public void testRemoveFollowRelationship() {
        dao.removeFollowRelationship("guy1", "guy3");
        Assertions.assertFalse(dao.isFollowing("guy1", "guy3"));
    }

    @Test
    public void testGetFollowedBy_PagedResponse() {
        List<String> aliases = dao.getFollowing("dummyUser", 10, null);
        Assertions.assertNotNull(aliases);
        Assertions.assertEquals(10, aliases.size());

        List<String> nextPage = dao.getFollowing("dummyUser", 10, aliases.get(9));
        Assertions.assertNotNull(nextPage);
        Assertions.assertEquals(10, nextPage.size());

        Assertions.assertNotEquals(aliases.get(9), nextPage.get(9));
    }

    @Test
    public void testGetFollowedBy_invalidUser_PagedResponse() {
        List<String> aliases = dao.getFollowing("fakeUser", 10, null);
        Assertions.assertNotNull(aliases);
        Assertions.assertEquals(0, aliases.size());
    }

    @Test
    public void testGetFollowedBy_nonPagedResponse() {
        dao.addFollowRelationship("guy1", "guy9");
        dao.addFollowRelationship("guy1", "guy6");
        List<String> following = dao.getFollowing("guy1");
        Assertions.assertNotNull(following);
        Assertions.assertEquals(2, following.size());
    }

    @Test
    public void testGetFollowedBy_invalidUser_nonPagedResponse() {
        List<String> following = dao.getFollowing("fakeUser");
        Assertions.assertNotNull(following);
        Assertions.assertEquals(0, following.size());
    }

    @Test
    public void testAddFollowersBatch() {
        StoredFollowRelationship one = new StoredFollowRelationship("guy1", "guy2");
        StoredFollowRelationship two = new StoredFollowRelationship("guy1", "guy3");
        StoredFollowRelationship three = new StoredFollowRelationship("guy1", "guy4");

        List<Object> batch = Arrays.asList(one, two, three);
        dao.addFollowersBatch(batch);

        Assertions.assertTrue(dao.isFollowing("guy1", "guy2"));
        Assertions.assertTrue(dao.isFollowing("guy1", "guy3"));
        Assertions.assertTrue(dao.isFollowing("guy1", "guy4"));

        dao.removeFollowRelationship("guy1", "guy2");
        dao.removeFollowRelationship("guy1", "guy3");
        dao.removeFollowRelationship("guy1", "guy4");

    }
}
