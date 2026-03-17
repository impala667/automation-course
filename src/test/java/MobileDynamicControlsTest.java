import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Locator;

import base.BaseTestMobile;

public class MobileDynamicControlsTest extends BaseTestMobile {
    @Test
    void testInputEnabling() {
        page.navigate("https://the-internet.herokuapp.com/dynamic_controls");
        Locator enable_field = page.getByRole(AriaRole.TEXTBOX);
        assertTrue(!enable_field.isEnabled());
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Enable")).click();
        Locator message = page.locator("#message");
        page.waitForSelector("#message");
        assertEquals("It's enabled!", message.textContent());
        assertTrue(enable_field.isEnabled());
    }
}
