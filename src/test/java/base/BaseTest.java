package base;

import com.microsoft.playwright.*;

import org.junit.jupiter.api.*;

import java.nio.file.Paths;

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
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("target/videos/")));
        page = context.newPage();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true) // Захватывать скриншоты
                .setSnapshots(true));

    }

    @AfterEach
    void tearDown() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("target/traces/trace-dynamic-loading.zip")));
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