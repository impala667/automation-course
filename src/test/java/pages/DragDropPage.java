package pages;
import components.DragDropArea;
import com.microsoft.playwright.Page;

public class DragDropPage extends BasePage {
    private DragDropArea dragDropArea;  

    public DragDropPage(Page page) {  
        super(page);  
    }  

    public DragDropArea dragDropArea() {  
        if (dragDropArea == null) {  
            dragDropArea = new DragDropArea(page);  
        }  
        return dragDropArea;  
    } 
}