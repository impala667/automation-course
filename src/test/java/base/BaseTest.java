package base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

@ExtendWith(ContextCaptureExtension.class)
public class BaseTest {
    protected ExtensionContext extensionContext; // <- теперь доступно в наследниках

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    public Page page;

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
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}