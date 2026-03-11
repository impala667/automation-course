package extensions;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import base.BaseTest;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import com.aventstack.extentreports.Status;
import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ScreenshotExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Object testInstance = context.getTestInstance().orElse(null);
        BaseTest baseTest = (BaseTest) testInstance;
        Page page = baseTest.page;
        byte[] screenshot;
        ExtentTest extentTest = baseTest.extentTest;
        try {
            screenshot = page.screenshot();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (context.getExecutionException().isPresent()) {
            String testName = context.getDisplayName();
            String attachmentName = "Скриншот_при_падении_" + testName + ".png";
            Allure.addAttachment(attachmentName, "image/png",
                    new ByteArrayInputStream(screenshot), "png");
            System.out.println("Тест упал: скриншот добавлен в Allure.");
            extentTest.log(Status.FAIL, "Ошибка: " + context.getExecutionException().get().getMessage());
        } else {
            String base64Screenshot = Base64.getEncoder().encodeToString(screenshot);
            extentTest.log(Status.PASS, "Скриншот после успешного выполнения",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            System.out.println("Тест успешен: скриншот добавлен в ExtentReports.");
        }
    }
}