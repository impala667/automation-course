import static org.junit.jupiter.api.Assertions.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.DisplayName;

import com.microsoft.playwright.Locator;
import com.aventstack.extentreports.Status;
import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.junit.jupiter.api.Test;

@Epic("Тестирование веб интерфейса the-internet.herokuapp.com")
@Feature("Страница JavaScript Alerts")
public class AdvancedReportingTest extends BaseTest {
    Locator button;

    @Test
    @Story("Работа с JS Alerts")
    @DisplayName("Тестирование JS Alerts")
    void testJavaScriptAlert() {
        navigateToJSAlertsPage();
        checkHeader();
        buttonCountCheck();
        buttonClickForAlert();
        resultClickForAlert();
        buttonClickForConfirm();
        resultClickForConfirm();
        buttonClickForPrompt();
        resultClickForPrompt();

    };

    @Step("Переход на страницу /javascript_alerts")
    private void navigateToJSAlertsPage() {
        page.navigate("https://the-internet.herokuapp.com/javascript_alerts");
        extentTest.log(Status.INFO, "Страница /javascript_alerts открыта");
    }

    @Step("Проверка заголовка страницы")
    private void checkHeader() {
        Locator pageHeader = page.locator("h3");
        assertTrue(pageHeader.isVisible());
        assertEquals("JavaScript Alerts", pageHeader.textContent());
        extentTest.log(Status.INFO, "Заголовок страницы проверен " + pageHeader.textContent());
    }

    @Step("Проверка количества кнопок")
    private void buttonCountCheck() {
        button = page.locator("button");
        assertEquals(3, button.count());
        extentTest.log(Status.INFO, "Кол-во кнопок проверено " + button.count());
    }

    @Step("Проверка диалога по кнопке Click for JS Alert")
    private void buttonClickForAlert() {
        final String[] dialogMessage = { null };
        page.onceDialog(dialog -> {
            dialogMessage[0] = dialog.message();
            dialog.accept();
        });
        Locator js_allert_button = button.filter(null)
                .filter(new Locator.FilterOptions().setHasText("Click for JS Alert"));
        js_allert_button.click();
        assertEquals("I am a JS Alert", dialogMessage[0]);
        extentTest.log(Status.INFO, "Текст диалога совпадает с ожидаемым результатом");
    }

    @Step("Проверка Результата в Result для JS Alert")
    private void resultClickForAlert() {
        Locator result = page.locator("#result");
        assertEquals("You successfully clicked an alert", result.textContent());
        assertThat(result).hasCSS("color", "rgb(0, 128, 0)");
        extentTest.log(Status.INFO, "Текст результата и цвет совпадает с ожидаемым результатом");
    }

    @Step("Проверка диалога по кнопке Click for JS Confirm")
    private void buttonClickForConfirm() {
        final String[] dialogMessage = { null };
        page.onceDialog(dialog -> {
            dialogMessage[0] = dialog.message();
            dialog.accept();
        });
        Locator js_Confirm_button = button.filter(null)
                .filter(new Locator.FilterOptions().setHasText("Click for JS Confirm"));
        js_Confirm_button.click();
        assertEquals("I am a JS Confirm", dialogMessage[0]);
        extentTest.log(Status.INFO, "Текст диалога совпадает с ожидаемым результатом");
    }

    @Step("Проверка Результата в Result для JS Confirm")
    private void resultClickForConfirm() {
        Locator result = page.locator("#result");
        assertEquals("You clicked: Ok", result.textContent());
        assertThat(result).hasCSS("color", "rgb(0, 128, 0)");
        extentTest.log(Status.INFO, "Текст результата и цвет совпадает с ожидаемым результатом");
    }

    @Step("Проверка диалога по кнопке Click for JS Prompt")
    private void buttonClickForPrompt() {
        final String[] dialogMessage = { null };
        page.onceDialog(dialog -> {
            dialogMessage[0] = dialog.message();
            dialog.accept("hi");
        });
        Locator js_Prompt_button = button.filter(null)
                .filter(new Locator.FilterOptions().setHasText("Click for JS Prompt"));
        js_Prompt_button.click();
        assertEquals("I am a JS prompt", dialogMessage[0]);
        extentTest.log(Status.INFO, "Текст диалога совпадает с ожидаемым результатом");
    }

    @Step("Проверка Результата в Result для JS Prompt")
    private void resultClickForPrompt() {
        Locator result = page.locator("#result");
        assertEquals("You entered: hi", result.textContent());
        assertThat(result).hasCSS("color", "rgb(0, 128, 0)");
        extentTest.log(Status.INFO, "Текст результата и цвет совпадает с ожидаемым результатом");
    }

}
