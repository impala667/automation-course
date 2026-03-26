package base;

import com.microsoft.playwright.*;

import org.junit.jupiter.api.*;

public class BaseTest {

    public Page page;
    Playwright playwright;
    Browser browser;
    BrowserContext context;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(1500));
        context = browser.newContext();
        page = context.newPage();

    }

    @AfterEach
    void tearDown() {

        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }

    }
}