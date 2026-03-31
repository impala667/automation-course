package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.config.EnvironmentConfig;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

public class StatusCodeTest {
    private EnvironmentConfig config;
    private Playwright playwright;
    private APIRequestContext request;

    @BeforeEach
    public void setup() {
        config = ConfigFactory.create(EnvironmentConfig.class, System.getenv());
        playwright = Playwright.create();
        request = playwright.request().newContext();
    }

    @Test
    public void test200() {
        APIResponse response = request.get(config.baseUrl() + "200");
        assertEquals(200, response.status());

    }

    @Test
    public void test404() {
        APIResponse response = request.get(config.baseUrl() + "404");
        assertEquals(404, response.status());

    }

    @Test
    public void test500() {
        APIResponse response = request.get(config.baseUrl() + "500");
        assertEquals(500, response.status());

    }

    @AfterEach
    public void tearDown() {
        if (request != null) {
            request.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

}
