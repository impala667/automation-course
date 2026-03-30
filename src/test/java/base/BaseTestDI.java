package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Page;

import managers.TestContextManager;

public class BaseTestDI {
    protected TestContextManager context;
    protected Page page;

    @BeforeEach
    void setUp() {
        context = new TestContextManager();
        page = context.createNewPage();
    }

    @AfterEach
    void tearDown() {
        page.close();
        context.close();
    }
}