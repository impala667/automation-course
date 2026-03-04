package base;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ContextCaptureExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        // Получаем экземпляр текущего тестового класса
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof BaseTest) {
            ((BaseTest) testInstance).extensionContext = context;
        }
    }
}