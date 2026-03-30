import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import base.BaseTestDI;
import pages.DynamicControlsPage;

public class DynamicControlsTest extends BaseTestDI {
    private DynamicControlsPage controlsPage;

    @Test
    public void testCheckboxRemoval() {
        controlsPage = new DynamicControlsPage(page);
        controlsPage.navigate();
        assertTrue(controlsPage.isCheckboxVisible());
        controlsPage.clickRemoveButton();
        controlsPage.waitForCheckboxHidden();
        assertFalse(controlsPage.isCheckboxVisible());
        assertTrue(controlsPage.isMessageVisible());
        assertEquals("It's gone!", controlsPage.MessageText());
        controlsPage.clickAddButton();
        controlsPage.waitForCheckboxVisible();
        assertTrue(controlsPage.isCheckboxVisible());
        assertTrue(controlsPage.isMessageVisible());
        assertEquals("It's back!", controlsPage.MessageText());
    }
}
