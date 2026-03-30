import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import base.BaseTest;

public class DynamicControlsTest2 extends BaseTest {
    @Test
    void testDynamicCheckbox() {
        page.navigate("https://the-internet.herokuapp.com/dynamic_controls");
        Locator checkbox = page.locator("input[type='checkbox']");
        assertTrue(checkbox.isVisible());
        Locator remove = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Remove"));
        remove.click();
        checkbox.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        assertFalse(checkbox.isVisible());
        Locator message = page.locator("#message");
        assertTrue(message.isVisible());
        assertEquals("It's gone!", message.textContent());
        Locator add = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Add"));
        add.click();
        checkbox.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertTrue(checkbox.isVisible());
        assertTrue(message.isVisible());
        assertEquals("It's back!", message.textContent());

    }
}
