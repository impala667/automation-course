import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Execution(ExecutionMode.CONCURRENT)
class AddRemoveElementsTest extends ParallelTestBase {

    @Test
    void testAddAndRemoveElement() {

        page.navigate("https://the-internet.herokuapp.com/add_remove_elements/");

        Locator addButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Element"));
        assertTrue(addButton.isVisible());
        addButton.click();

        Locator deleteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete"));
        assertTrue(deleteButton.isVisible());
        assertEquals(1, deleteButton.count());

        deleteButton.click();
        
        assertFalse(deleteButton.isVisible());
    }

    @Test
    void testMultipleElementsCanBeAdded() {

        page.navigate("https://the-internet.herokuapp.com/add_remove_elements/");

        Locator addButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Element"));
        addButton.click();
        addButton.click();
        addButton.click();

        Locator deleteButtons = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete"));
        assertEquals(3, deleteButtons.count());
    }
}