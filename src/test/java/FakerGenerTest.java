
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Route;
import com.microsoft.playwright.Locator;
import base.BaseTest;

public class FakerGenerTest extends BaseTest {
    @Test
    void testFakerGener() {
        Faker faker = new Faker();
        String fake_name = faker.name().fullName();
        page.route("**/dynamic_content", route -> {
            APIResponse response = route.fetch();
            String originalHtml = response.text();
            String modifiedHtml = originalHtml.replaceFirst(
                    "(?s)<div\\s+class=\'large-10\\s+columns\'>.*?</div>",
                    "<div class=\'large-10 columns\'>" + fake_name + "</div>");
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(response.status())
                    .setHeaders(response.headers())
                    .setBody(modifiedHtml));
        });
        Locator changed_name = page.locator("#content > div:nth-child(1) > div.large-10.columns");
        page.navigate("https://the-internet.herokuapp.com/dynamic_content");
        assertEquals(fake_name, changed_name.textContent());

    }
}
