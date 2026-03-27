package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public abstract class BasePage {
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }


    public void open(String url) {
        page.navigate(url);
        waitForPageLoaded();
    }

    public void waitForPageLoaded() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }


}