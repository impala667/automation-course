import base.BaseTest;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

//import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.*;
public class CartTest extends BaseTest{
    @Test
    void CartScreenshot() {
        page.navigate("https://the-internet.herokuapp.com/add_remove_elements/");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Element")).click();
        Locator cart_elements = page.locator("#content");

        cart_elements.screenshot(new Locator.ScreenshotOptions().setPath(getTimestampPath("element_added.png")));
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete")).click();
        cart_elements.screenshot(new Locator.ScreenshotOptions().setPath(getTimestampPath("element_deleted.png")));

    }

    private Path getTimestampPath(String filename) {
        return Paths.get(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + "/" + filename);
    }
}
