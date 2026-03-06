import com.microsoft.playwright.Locator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Execution(ExecutionMode.CONCURRENT)
class LoginPageTest extends ParallelTestBase {

    @Test
    void testLoginPageHeader() {

        page.navigate("https://the-internet.herokuapp.com/login");
        Locator pageHeader = page.locator("h2");
        assertTrue(pageHeader.isVisible());
        assertEquals("Login Page", pageHeader.textContent());
    }

    @Test
    void testLoginButtonIsPresent() {

        page.navigate("https://the-internet.herokuapp.com/login");
        Locator loginButton = page.locator("button[type='submit']");
        assertTrue(loginButton.isVisible());
        assertTrue(loginButton.isEnabled());
    }
}