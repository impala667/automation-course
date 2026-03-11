import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

import com.microsoft.playwright.Locator;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.junit.jupiter.api.Test;

@Epic("Тестирование веб интерфейса the-internet.herokuapp.com")
@Feature("Страница Checkboxes")
public class CheckboxTest extends BaseTest {
    Locator checkbox;

    @Test
    @Story("Проверка работы чекбоксов")
    @DisplayName("Тестирование выбора/снятия чекбоксов")
    void testCheckboxes() {
        navigateToCheckboxesPage();
        checkHeader();
        checkboxesCountCheck();
        checkboxesChangeState();
        returnCheckboxesStateBack();
    };

    @Step("Переход на страницу /checkboxes")
    private void navigateToCheckboxesPage() {
        page.navigate("https://the-internet.herokuapp.com/checkboxes");
    }

    @Step("Проверка заголовка страницы")
    private void checkHeader() {
        Locator pageHeader = page.locator("h3");
        assertTrue(pageHeader.isVisible());
        assertEquals("Checkboxes", pageHeader.textContent());
    }

    @Step("Проверка количества чекбоксов")
    private void checkboxesCountCheck() {
        checkbox = page.locator("input[type=checkbox]");
        assertEquals(2, checkbox.count());
    }

    @Step("Проверка изменения состояния чекбоксов")
    private void checkboxesChangeState() {
        for (int i = 0; i < 2; i++) {
            assertTrue(checkbox.nth(i).isEnabled());
            if (checkbox.nth(i).isChecked()) {
                checkbox.nth(i).click();
                assertTrue(!checkbox.nth(i).isChecked());
            } else {
                checkbox.nth(i).click();
                assertTrue(checkbox.nth(i).isChecked());
            }
        }
    }

    @Step("Возврат состояния чекбоксов на начальное")
    private void returnCheckboxesStateBack() {
        for (int i = 0; i < 2; i++) {
            checkbox.nth(i).click();
        }
    }
}
