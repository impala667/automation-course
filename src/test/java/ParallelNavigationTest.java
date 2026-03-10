
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import java.util.List;
import java.util.regex.Pattern;

@Execution(ExecutionMode.CONCURRENT)
public class ParallelNavigationTest {

    @ParameterizedTest
    @MethodSource("browserAndPageProvider")
    void testPageLoad(String browserType, String pageUrl) {

        try (Playwright playwright = Playwright.create()) {
            BrowserType type = switch (browserType.toLowerCase()) {
                case "chromium" -> playwright.chromium();
                case "firefox" -> playwright.firefox();
                default -> throw new IllegalArgumentException("Неподдерживаемый браузер: " + browserType);
            };

            try (Browser browser = type.launch(new BrowserType.LaunchOptions().setHeadless(true))) {
                try (BrowserContext context = browser.newContext()) {
                    Page page = context.newPage();
                    System.out.printf("Тест для браузера %s и URL %s запущен в потоке %s%n",
                            browserType, pageUrl, Thread.currentThread().getName());
                    page.navigate("https://the-internet.herokuapp.com" + pageUrl);
                    assertThat(page).hasTitle(Pattern.compile(".*"));
                }
            }
        }
    }

    static Stream<Arguments> browserAndPageProvider() {
        List<String> browsers = List.of("chromium", "firefox");
        List<String> urls = List.of(
                "/login",
                "/checkboxes",
                "/hover");
        return browsers.stream()
                .flatMap(browser -> urls.stream().map(url -> Arguments.of(browser, url)));
    }
}