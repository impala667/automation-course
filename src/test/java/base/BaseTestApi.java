package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.APIRequestContext;

import com.microsoft.playwright.Playwright;


public class BaseTestApi {
    Playwright playwright;
    public APIRequestContext requestContext;
    public APIResponse response;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://jsonplaceholder.typicode.com"));

    }

    @AfterEach
    void tearDown() {
        response.dispose();
        requestContext.dispose();
        playwright.close();
    }
}
