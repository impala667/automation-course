package base;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.APIRequestContext;
import java.nio.file.Paths;
import java.nio.file.Files;
import com.microsoft.playwright.Playwright;

public class BaseTestApi {
    protected Playwright playwright;
    protected APIRequestContext requestContext;
    protected APIResponse response;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://httpbin.org/"));

    }

    @AfterEach
    void tearDown() {
        if (response != null) {
            response.dispose();
        }
        if (requestContext != null) {
            requestContext.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
        try {
            Files.deleteIfExists(Paths.get("target/downloaded_image.png"));
        } catch (IOException e) {
            System.err.println("Не удалось удалить временный файл: " + e.getMessage());
        }
    }
}
