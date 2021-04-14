package com.nathanielbennett.tweeter.server.dao;

import com.nathanielbennett.tweeter.model.domain.AuthToken;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.util.PasswordHasher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthTokenDAOTest {
    AuthTokenDAO dao;

    @BeforeEach
    public void setup() {
        dao = new AuthTokenDAO();
    }

    @Test
    public void testCreateToken() {
        AuthToken token = dao.createToken("testUser");
        Assertions.assertNotNull(token);
    }

    @Test
    public void testCheckToken_validToken() {
        AuthToken token = dao.createToken("testUser");
        Assertions.assertTrue(dao.checkToken(token, "testUser"));
    }

    @Test
    public void testCheckToken_invalidToken() {
        Assertions.assertFalse(dao.checkToken(new AuthToken(), "testUser"));
    }

    @Test
    public void testCheckToken_validToken_invalidUser(){
        AuthToken token = dao.createToken("testUser");
        Assertions.assertFalse(dao.checkToken(token, "HACKER"));
    }

    @Test
    public void testDeleteToken() {
        AuthToken token = dao.createToken("testUser");
        Assertions.assertTrue(dao.checkToken(token, "testUser"));
        dao.deleteToken(token.getAuthTokenID(), "testUser");
    }
}
