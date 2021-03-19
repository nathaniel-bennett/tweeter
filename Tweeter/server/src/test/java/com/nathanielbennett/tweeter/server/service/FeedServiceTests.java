package com.nathanielbennett.tweeter.server.service;

import com.nathanielbennett.tweeter.server.dao.FeedDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class FeedServiceTests {

    private Service feedService;

    @BeforeEach
    public void setup() {
        FeedDAO feedDAO = new FeedDAO();
        feedService = Mockito.spy(FeedServiceImpl.class);
        Mockito.when(feedService.getDao(FeedDAO.class)).thenReturn(feedDAO);
    }

    @Test
    public void pleaseWord() throws IOException {
        FeedDAO feedDAO = feedService.getDao(FeedDAO.class);

    }
}
