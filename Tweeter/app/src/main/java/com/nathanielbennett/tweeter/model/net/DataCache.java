package com.nathanielbennett.tweeter.model.net;

import com.nathanielbennett.tweeter.model.domain.Status;
import com.nathanielbennett.tweeter.model.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataCache {

    private static DataCache instance;

    public static DataCache getInstance(){
        if (instance == null){
            instance = new DataCache();
        }
        return instance;
    }

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private Map<String, User> allUsers = new HashMap<>();
    private Map<User, ArrayList<Status>> statusMap = new HashMap<>();
    private Map<User, ArrayList<User>> followerMap = new HashMap<>();
    private Map<User, ArrayList<User>> followingMap = new HashMap<>();

    private DataCache(){
        // Set up users in backend
        allUsers.put("@AllenAnderson", new User("Allen", "Anderson", MALE_IMAGE_URL));
        allUsers.put("@AmyAmes", new User("Amy", "Ames", FEMALE_IMAGE_URL));
        allUsers.put("@BobBobson", new User("Bob", "Bobson", MALE_IMAGE_URL));
        allUsers.put("@BonnieBeatty", new User("Bonnie", "Beatty", FEMALE_IMAGE_URL));
        allUsers.put("@ChrisColston", new User("Chris", "Colston", MALE_IMAGE_URL));
        allUsers.put("@CindyCoats", new User("Cindy", "Coats", FEMALE_IMAGE_URL));
        allUsers.put("@DanDonaldson", new User("Dan", "Donaldson", MALE_IMAGE_URL));
        allUsers.put("@DeeDempsey", new User("Dee", "Dempsey", FEMALE_IMAGE_URL));
        allUsers.put("@ElliottEnderson", new User("Elliott", "Enderson", MALE_IMAGE_URL));
        allUsers.put("@ElizabethEngle", new User("Elizabeth", "Engle", FEMALE_IMAGE_URL));
        allUsers.put("@FrankFrandson", new User("Frank", "Frandson", MALE_IMAGE_URL));
        allUsers.put("@FranFranklin", new User("Fran", "Franklin", FEMALE_IMAGE_URL));
        allUsers.put("@GaryGilbert", new User("Gary", "Gilbert", MALE_IMAGE_URL));
        allUsers.put("@GiovannaGiles", new User("Giovanna", "Giles", FEMALE_IMAGE_URL));
        allUsers.put("@HenryHenderson", new User("Henry", "Henderson", MALE_IMAGE_URL));
        allUsers.put("@HelenHopwell", new User("Helen", "Hopwell", FEMALE_IMAGE_URL));
        allUsers.put("@IgorIsaacson", new User("Igor", "Isaacson", MALE_IMAGE_URL));
        allUsers.put("@IsabelIsaacson", new User("Isabel", "Isaacson", FEMALE_IMAGE_URL));
        allUsers.put("@JustinJones", new User("Justin", "Jones", MALE_IMAGE_URL));
        allUsers.put("@JillJohnson", new User("Jill", "Johnson", FEMALE_IMAGE_URL));
        allUsers.put("dummyUser", new User("Dummy", "User", "dummyUser", MALE_IMAGE_URL));

        // Create user stores
        Set<String> usernames = allUsers.keySet();
        for (String s : usernames) {
            statusMap.put(allUsers.get(s), new ArrayList<>());
            followerMap.put(allUsers.get(s), new ArrayList<>());
            followingMap.put(allUsers.get(s), new ArrayList<>());
        }

        // Add dummy User statuses
        statusMap.get(allUsers.get("dummyUser")).add(new Status(allUsers.get("dummyUser"), "Hello Status!", "Feburary 17 2021 9:16 PM", new ArrayList<User>()));
        statusMap.get(allUsers.get("dummyUser")).add(new Status(allUsers.get("dummyUser"), "Goodbye Status!", "Feburary 17 2021 9:17 PM", new ArrayList<User>()));
        statusMap.get(allUsers.get("dummyUser")).add(new Status(allUsers.get("dummyUser"), "I would like to mention @AllenAnderson and @HelenHopwell", "February 17 2021 9:18 PM", Arrays.asList(allUsers.get("@AllenAnderson"), allUsers.get("@HelenHopwell"))));
        statusMap.get(allUsers.get("dummyUser")).add(new Status(allUsers.get("dummyUser"), "Chris is cool!", "Feburary 17 2021 9:19 PM", new ArrayList<User>()));
        statusMap.get(allUsers.get("dummyUser")).add(new Status(allUsers.get("dummyUser"), "Nathan is neat!", "Feburary 17 2021 9:20 PM", new ArrayList<User>()));

        // Add other user statuses
        statusMap.get(allUsers.get("@AllenAnderson")).add(new Status(allUsers.get("@AllenAnderson"), "I miss coding at work.", "Feburary 18 2021 9:11 PM", new ArrayList<>()));
        statusMap.get(allUsers.get("@AmyAmes")).add(new Status(allUsers.get("@AmyAmes"), "I really don't like fake news.", "Feburary 18 2021 9:12 PM", new ArrayList<>()));
        statusMap.get(allUsers.get("@BobBobson")).add(new Status(allUsers.get("@BobBobson"), "I hope @ChrisColston is doing well.", "Feburary 18 2021 9:13 PM", Arrays.asList(allUsers.get("@ChrisColston"))));
        statusMap.get(allUsers.get("@BonnieBeatty")).add(new Status(allUsers.get("@BonnieBeatty"), "I hate using google.com.", "Feburary 18 2021 9:14 PM", new ArrayList<>()));
        statusMap.get(allUsers.get("@ChrisColston")).add(new Status(allUsers.get("@ChrisColston"), "Hello world.", "Feburary 18 2021 9:15 PM", new ArrayList<>()));
        statusMap.get(allUsers.get("@CindyCoats")).add(new Status(allUsers.get("@CindyCoats"), "Visit csmcclain.com for an awesome video.", "Feburary 18 2021 9:16 PM", new ArrayList<>()));

        // Add dummy User Followers
        for (String name : allUsers.keySet()) {
            followerMap.get(allUsers.get("dummyUser")).add(allUsers.get(name));
        }


        // Add other Users followers
        List<User> groupOne = new ArrayList<>();
        groupOne.add(allUsers.get("@AllenAnderson"));
        groupOne.add(allUsers.get("@AmyAmes"));
        groupOne.add(allUsers.get("@BobBobson"));

        List<User> groupTwo = new ArrayList<>();
        groupTwo.add(allUsers.get("@BobBobson"));
        groupTwo.add(allUsers.get("@BonnieBeatty"));
        groupTwo.add(allUsers.get("@ChrisColston"));

        List<User> groupThree = new ArrayList<>();
        groupThree.add(allUsers.get("@BonnieBeatty"));
        groupThree.add(allUsers.get("@ChrisColston"));
        groupThree.add(allUsers.get("@CindyCoats"));

        int factor = 0;

        for (String s: allUsers.keySet()) {
            List<User> users;
            if (factor % 3 == 0) {
                users = groupThree;
            } else if (factor % 3 == 1) {
                users = groupTwo;
            } else {
                users = groupOne;
            }
            followerMap.get(allUsers.get(s)).addAll(users);
            factor++;
        }

        // Add dummy User Following
        for (String name : allUsers.keySet()) {
            followingMap.get(allUsers.get("dummyUser")).add(allUsers.get(name));
        }

        factor = 3;

        for (String s : allUsers.keySet()) {
            List<User> users;
            if (factor % 3 == 0) {
                users = groupTwo;
            } else if (factor % 3 == 1) {
                users = groupOne;
            } else {
                users = groupThree;
            }
            followingMap.get(allUsers.get(s)).addAll(users);
            factor++;
        }

        User dummy = allUsers.get("dummyUser");
        followerMap.get(allUsers.get("dummyUser")).remove(dummy);
        followingMap.get(allUsers.get("dummyUser")).remove(dummy);

//        private final Status user7Status = new Status(user7, "It's snowing outside right now.", "Feburary 18 2021 9:17 PM", new ArrayList<>());
//        private final Status user8Status = new Status(user8, "Amazing Time at the beach.", "Feburary 18 2021 9:18 PM", new ArrayList<>());
//        private final Status user9Status = new Status(user9, "@JillJohnson is awesome.", "Feburary 18 2021 9:19 PM", Arrays.asList(user20));
//        private final Status user10Status = new Status(user10, "Missing swimming.", "Feburary 18 2021 9:20 PM", new ArrayList<>());
//        private final Status user11Status = new Status(user11, "Live, Laugh, Love.", "Feburary 18 2021 9:21 PM", new ArrayList<>());
//        private final Status user12Status = new Status(user12, "I enjoy the outdoors.", "Feburary 18 2021 9:22 PM", new ArrayList<>());
//        private final Status user13Status = new Status(user13, "The programmers of this app did an amazing job.", "Feburary 18 2021 9:23 PM", new ArrayList<>());
//        private final Status user14Status = new Status(user14, "I hope that tomorrow will be snowy.", "Feburary 18 2021 9:24 PM", new ArrayList<>());
//        private final Status user15Status = new Status(user15, "Wake me up, when september ends!", "Feburary 18 2021 9:25 PM", new ArrayList<>());
    }

    public User getUser(String username) {
        return allUsers.get(username);
    }

    public ArrayList<Status> getStatuses(User user) {
        return statusMap.get(user);
    }

    public ArrayList<User> getFollowers(User user) {
        return followerMap.get(user);
    }

    public ArrayList<User> getFollowing(User user) {
        return followingMap.get(user);
    }

}
