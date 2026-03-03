import base.BaseTest;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class frames extends BaseTest {
    @Test
    void testNestedFrames() {
        page.navigate("https://the-internet.herokuapp.com/nested_frames");
        Frame left = page.frame("frame-left");
        assertTrue(left.locator("body").innerText().contains("LEFT"));
        Frame middle = page.frame("frame-middle");
        assertTrue(middle.locator("body").innerText().contains("MIDDLE"));
        // page.mainFrame();
        Page second = page.context().newPage();
        second.navigate("https://the-internet.herokuapp.com/nested_frames");
        second.close();
    }
}
