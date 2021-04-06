package com.nathanielbennett.tweeter.server.massUser;

import com.nathanielbennett.tweeter.model.domain.User;
import com.nathanielbennett.tweeter.server.dao.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class MassUserRegister {

    // How many follower users to add
    // We recommend you test this with a smaller number first, to make sure it works for you
    private final static int NUM_USERS = 10000;

    // The alias of the user to be followed by each user created
    // This example code does not add the target user, that user must be added separately.
    private final static String FOLLOW_TARGET = "dummyUser";

    public static void fillDatabase() {
        // Get instance of DAOs
        UserDAO userDao = new UserDAO();

        List<User> users = new ArrayList<>();

        for (int i = 1; i <= NUM_USERS;  i++) {

        }
    }


}