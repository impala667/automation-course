package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BaseTestMobile {

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
        Browser.NewContextOptions iPad11Pro = new Browser.NewContextOptions()
                .setViewportSize(834, 1194)
                .setUserAgent(
                        "Mozilla/5.0 (iPad; CPU OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/26.0 Mobile/15E148 Safari/604.1")
                .setIsMobile(true)
                .setDeviceScaleFactor(2)
                .setHasTouch(true);
        context = browser.newContext(iPad11Pro);
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
