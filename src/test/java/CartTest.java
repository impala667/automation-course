import base.BaseTest;
import io.qameta.allure.Allure;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.io.ByteArrayInputStream;
//import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.*;

public class CartTest extends BaseTest {
    @Test
    void CartScreenshot() {
        page.navigate("https://the-internet.herokuapp.com/add_remove_elements/");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Element")).click();
        Locator cart_elements = page.locator("#content");

        cart_elements.screenshot(new Locator.ScreenshotOptions().setPath(getTimestampPath("element_added.png")));
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete")).click();
        cart_elements.screenshot(new Locator.ScreenshotOptions().setPath(getTimestampPath("element_deleted.png")));

    };

    private Path getTimestampPath(String filename) {
        return Paths.get("target/screenshots/"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + "/" + filename);
    };

    @AfterEach
    void attachScreenshotOnFailure() {
        // Используем поле, унаследованное от BaseTest
        if (extensionContext.getExecutionException().isPresent()) {
            System.out.println("🔍 Тест упал, делаем скриншот");
            try {
                byte[] screenshot = page.screenshot();
                Allure.addAttachment("Screenshot on Failure", "image/png",
                        new ByteArrayInputStream(screenshot), ".png");
                System.out.println("✅ Скриншот добавлен в Allure");
            } catch (Exception e) {
                System.out.println("❌ Ошибка при создании скриншота: " + e.getMessage());
            }
        } else {
            System.out.println("✅ Тест успешен, скриншот не нужен");
        }
    }
}
