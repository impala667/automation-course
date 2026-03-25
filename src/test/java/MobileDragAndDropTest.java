import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Locator;
import base.BaseTestMobile;

public class MobileDragAndDropTest extends BaseTestMobile {
    @Test
    void testDragAndDropMobile() {
        page.navigate("https://the-internet.herokuapp.com/drag_and_drop");

        Locator columnA = page.locator("#column-a");
        Locator columnB = page.locator("#column-b");
        assertEquals("B", columnB.textContent());
        assertEquals("A", columnA.textContent());
        columnA.dragTo(columnB);
        assertEquals("A", columnB.textContent());
        assertEquals("B", columnA.textContent());

    }
}
