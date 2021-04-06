package com.nathanielbennett.tweeter.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {

    @Test
    public void testSameHashEveryTime() {

        String hash1 = BCrypt.hashpw("hello world", "$2a$10$w4Yhe34QPsYgpEwXuXbJ30");
        String hash2 = BCrypt.hashpw("hello world", "$2a$10$w4Yhe34QPsYgpEwXuXbJ30");

        Assertions.assertEquals(hash1, hash2);
    }
}
