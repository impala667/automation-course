package managers;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;

public class TestContextManager {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;

    public TestContextManager() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new LaunchOptions().setHeadless(true));
        context = browser.newContext();
    }

    public Page createNewPage() {
        return context.newPage();
    }

    public void close() {
        if (context != null)
            context.close();
        if (browser != null)
            browser.close();
        if (playwright != null)
            playwright.close();
    }
}
