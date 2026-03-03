import base.BaseTest;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.*;

import java.util.Map;

public class StatusCodeInterceptionTest extends BaseTest {
    @Test
    void statusCodeInterception() {
        page.navigate("https://the-internet.herokuapp.com/status_codes");
        page.route("**/status_codes/404", route -> {
            // Создаем мок-ответ
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(200)
                    .setBody("Mocked Success Response")
                    .setHeaders(Map.of("Content-Type", "text/html;charset=utf-8")));
        });
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("404")).click();
        // page.pause();
        Locator newText = page.locator("body");
        assertThat(newText).hasText("Mocked Success Response");
    }
}