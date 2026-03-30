package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.Page.GetByRoleOptions;

public class DynamicControlsPage {
    private Page page;
    private final Locator remove;
    private final Locator add;
    private final Locator checkbox;
    private final Locator message;

    public DynamicControlsPage(Page page) {
        this.page = page;
        this.remove = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Remove"));
        this.add = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Add"));
        this.checkbox = page.locator("input[type='checkbox']");
        this.message = page.locator("#message");
    }

    public void navigate() {
        page.navigate("https://the-internet.herokuapp.com/dynamic_controls");
    }

    public void clickRemoveButton() {
        remove.click();
    }

    public void clickAddButton() {
        add.click();
    }

    public boolean isCheckboxVisible() {
        return checkbox.isVisible();
    }

    public void waitForCheckboxVisible() {
        checkbox.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public void waitForCheckboxHidden() {
        checkbox.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }

    public boolean isMessageVisible() {
        return message.isVisible();
    }

    public String MessageText() {
        return message.textContent();
    }

}