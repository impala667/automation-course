package extensions;

import com.microsoft.playwright.Page;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.ByteArrayInputStream;

public class ScreenshotExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        context.getExecutionException().ifPresent(throwable -> {
            Object testInstance = context.getTestInstance().orElse(null);
            BaseTest baseTest = (BaseTest) testInstance;
            Page page = baseTest.page; 
            if (page != null) {
                try {
                    byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));

                    String testName = context.getDisplayName();
                    String attachmentName = "Скриншот_при_падении_" + testName + ".png";

                    Allure.addAttachment(attachmentName, "image/png", new ByteArrayInputStream(screenshot), "png");
                    System.out.println("Тест упал, скриншот добавлен в Allure.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}