package com.nathanielbennett.tweeter.client.model.net;

import com.nathanielbennett.tweeter.client.model.net.ServerFacade;
import com.nathanielbennett.tweeter.model.domain.User;

class ServerFacadeTest {

    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", "");
    private final User user6 = new User("Mother", "Teresa", "");
    private final User user7 = new User("Harriett", "Hansen", "");
    private final User user8 = new User("Zoe", "Zabriski", "");

    private ServerFacade serverFacadeSpy;

    /*

    @BeforeEach
    void setup() {
        serverFacadeSpy = Mockito.spy(new ServerFacade());
    }

    @Test
    void testGetFollowees_noFolloweesForUser() {
        List<User> followees = Collections.emptyList();
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user1.getAlias(), 10, null);
        FollowResponse response = serverFacadeSpy.getFollowing(request);

        Assertions.assertEquals(0, response.getRequestedUsers().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {
        List<User> followees = Collections.singletonList(user2);
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user1.getAlias(), 10, null);
        FollowResponse response = serverFacadeSpy.getFollowing(request);

        Assertions.assertEquals(1, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user2));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() {
        List<User> followees = Arrays.asList(user2, user3);
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user3.getAlias(), 2, null);
        FollowResponse response = serverFacadeSpy.getFollowing(request);

        Assertions.assertEquals(2, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user2));
        Assertions.assertTrue(response.getRequestedUsers().contains(user3));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {
        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7);
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user5.getAlias(), 2, null);
        FollowResponse response = serverFacadeSpy.getFollowing(request);

        // Verify first page
        Assertions.assertEquals(2, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user2));
        Assertions.assertTrue(response.getRequestedUsers().contains(user3));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FollowRequest(user5.getAlias(), 2, response.getRequestedUsers().get(1).getAlias());
        response = serverFacadeSpy.getFollowing(request);

        Assertions.assertEquals(2, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user4));
        Assertions.assertTrue(response.getRequestedUsers().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FollowRequest(user5.getAlias(), 2, response.getRequestedUsers().get(1).getAlias());
        response = serverFacadeSpy.getFollowing(request);

        Assertions.assertEquals(2, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user6));
        Assertions.assertTrue(response.getRequestedUsers().contains(user7));
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() {
        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        FollowRequest request = new FollowRequest(user6.getAlias(), 2, null);
        FollowResponse response = serverFacadeSpy.getFollowing(request);

        // Verify first page
        Assertions.assertEquals(2, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user2));
        Assertions.assertTrue(response.getRequestedUsers().contains(user3));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FollowRequest(user6.getAlias(), 2, response.getRequestedUsers().get(1).getAlias());
        response = serverFacadeSpy.getFollowing(request);

        Assertions.assertEquals(2, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user4));
        Assertions.assertTrue(response.getRequestedUsers().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FollowRequest(user6.getAlias(), 2, response.getRequestedUsers().get(1).getAlias());
        response = serverFacadeSpy.getFollowing(request);

        Assertions.assertEquals(2, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user6));
        Assertions.assertTrue(response.getRequestedUsers().contains(user7));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify fourth page
        request = new FollowRequest(user6.getAlias(), 2, response.getRequestedUsers().get(1).getAlias());
        response = serverFacadeSpy.getFollowing(request);

        Assertions.assertEquals(1, response.getRequestedUsers().size());
        Assertions.assertTrue(response.getRequestedUsers().contains(user8));
        Assertions.assertFalse(response.getHasMorePages());
    }

     */
}
