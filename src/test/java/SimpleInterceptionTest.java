import base.BaseTest;
import com.microsoft.playwright.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.*;

public class SimpleInterceptionTest extends BaseTest {
    @Test
    void interceptPost() {
        page.navigate("https://the-internet.herokuapp.com/login");
        page.route("**/authenticate", route -> {
            System.out.println("Запрос перехвачен!");
            String prev_user = route.request().postData();
            // Создаем мок-ответ
            Route.ResumeOptions options = new Route.ResumeOptions()
                    .setPostData("username=HACKED_USER&password=SuperSecretPassword!".getBytes());
            route.resume(options);
            String new_user = route.request().postData();
            System.out.println("Было: " + prev_user + ',' + " Стало: " + new_user);
        });
        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("SuperSecretPassword!");
        page.locator("button").click();
        assertThat(page.locator("#flash-messages")).isVisible();
        assertThat(page.locator("#flash-messages")).containsText("Your username is invalid!");

    }

}
