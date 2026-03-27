package components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DragDropArea {
    private final Locator columns;

    public DragDropArea(Page page) {
        this.columns = page.locator("#columns");
    }

    public void dragAToB() {
        Locator elementA = columns.locator("#column-a");
        Locator elementB = columns.locator("#column-b");
        elementA.dragTo(elementB);
    }

    public String getTextB() {
        return columns.locator("#column-b").textContent();
    }
}
