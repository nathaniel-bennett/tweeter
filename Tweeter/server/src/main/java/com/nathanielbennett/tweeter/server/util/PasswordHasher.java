package com.nathanielbennett.tweeter.server.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public String hash(String plaintextPassword) {
        return BCrypt.hashpw(plaintextPassword, BCrypt.gensalt(12));
    }
}
