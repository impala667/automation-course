import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import base.BaseTest;

public class HoverTest extends BaseTest {
    @Test
    void testHoverProfiles() {
        page.navigate("https://the-internet.herokuapp.com/hovers");
        Locator profiles = page.locator(".figure");
        for (int i = 0; i < profiles.count(); i++) {
            profiles.nth(i).hover();
            Locator username = profiles.nth(i).locator("h5");
            Locator profile_link = profiles.nth(i).getByRole(AriaRole.LINK);
            assertTrue(username.isVisible());
            assertEquals("name: user" + (i + 1), username.textContent());
            assertTrue(profile_link.isVisible());
            assertEquals("View profile", profile_link.textContent());
            profile_link.click();
            String profile_url = page.url();
            assertTrue(profile_url.contains("users/" + (i + 1)));
            page.goBack();

        }
    }
}
