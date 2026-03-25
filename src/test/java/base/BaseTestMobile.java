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
        Browser.NewContextOptions samsungS22Ultra = new Browser.NewContextOptions()
                .setViewportSize(412, 915)
                .setUserAgent(
                        "Mozilla/5.0 (Linux; Android 13; SM-S908U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Mobile Safari/537.36")
                .setIsMobile(true)
                .setDeviceScaleFactor(3.5)
                .setHasTouch(true);

        context = browser.newContext(samsungS22Ultra);
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
