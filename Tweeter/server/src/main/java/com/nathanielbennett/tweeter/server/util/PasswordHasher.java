package com.nathanielbennett.tweeter.server.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public String hash(String plaintextPassword) {
        return BCrypt.hashpw(plaintextPassword, "$2a$10$w4Yhe34QPsYgpEwXuXbJ30");
    }
}
