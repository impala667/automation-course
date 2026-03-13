import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import base.BaseTest;

public class DynamicLoadingTest extends BaseTest {
    @Test
    void testDynamicLoadingWithTrace() {

        Response response = page.waitForResponse("https://the-internet.herokuapp.com/dynamic_loading/1", () -> {
            page.navigate("https://the-internet.herokuapp.com/dynamic_loading");
            page.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("Example 1: Element on page that is hidden"))
                    .click();
        });
        Integer status = response.status();
        assertEquals(200, status);
        Locator hidden_element = page.locator("#finish");
        assertFalse(hidden_element.isVisible());
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Start"))
                .click();
        page.waitForSelector("#finish", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        assertTrue(hidden_element.isVisible());
        assertThat(hidden_element).hasText("Hello World!");
    }
}
