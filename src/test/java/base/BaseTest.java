package base;

import com.microsoft.playwright.*;

import extensions.ScreenshotExtension;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import java.nio.file.Paths;

@ExtendWith(ScreenshotExtension.class)
public class BaseTest {

    public Page page;

    Playwright playwright;
    Browser browser;
    BrowserContext context;

    @Step("Инициализация браузера, контекста и страницы")
    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(1500));
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("target/videos/")));
        page = context.newPage();
    }

    @Step("Закрытие браузера, контекста и страницы")
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