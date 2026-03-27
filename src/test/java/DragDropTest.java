import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pages.DragDropPage;
import static org.junit.jupiter.api.Assertions.*;

public class DragDropTest {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
                .setSlowMo(1500));
    }

    @BeforeEach
    void createPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closePage() {
        context.close();
        page.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @Test
    public void testDragAndDrop() {
        DragDropPage dragDropPage = new DragDropPage(page);
        dragDropPage.open("https://the-internet.herokuapp.com/drag_and_drop");
        dragDropPage.dragDropArea().dragAToB();
        assertEquals("A", dragDropPage.dragDropArea().getTextB());
    }
}