package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;

import extensions.ScreenshotExtension;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import java.nio.file.Paths;

@ExtendWith(ScreenshotExtension.class)
public class BaseTest {
    public ExtentTest extentTest;
    private static ExtentReports extent;
    public Page page;
    Playwright playwright;
    Browser browser;
    BrowserContext context;

    @Step("Инициализация ExtentReport")
    @BeforeAll
    static void setup() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report.html");
        reporter.config().setDocumentTitle("Playwright Test Report");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @Step("Инициализация браузера, контекста и страницы")
    @BeforeEach
    void setUp(TestInfo testInfo) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(1500));
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("target/videos/")));
        page = context.newPage();
        extentTest = extent.createTest(testInfo.getDisplayName());
        extentTest.log(Status.INFO, "Инициализация браузера, контекста и страницы");
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
        extentTest.log(Status.INFO, "Закрытие браузера, контекста и страницы");
    }

    @Step("Завершение и сохранение ExtentReport")
    @AfterAll
    static void tearDownAll() {

        if (extent != null) {
            extent.flush();
        }
    }
}