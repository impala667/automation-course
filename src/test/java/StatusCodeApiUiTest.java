import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Response;
import base.BaseTestApi;

public class StatusCodeApiUiTest extends BaseTestApi {
    @Test
    void testStatusCodesCombined() {
        assertEquals(getApiStatusCode(200), getUiStatusCode(200));
        page.goBack();
        page.waitForLoadState();
        assertEquals(getApiStatusCode(404), getUiStatusCode(404));
    }

    private int getApiStatusCode(int code) {
        response = requestContext.get("/status_codes/" + code);
        assertEquals(code, response.status());
        return response.status();
    }

    private int getUiStatusCode(int code) {
        Response response = page.waitForResponse("/status_codes/" + code, () -> {
            page.getByRole(AriaRole.LINK, new GetByRoleOptions().setName(String.valueOf(code))).click();
        });
        assertEquals(code, response.status());
        return response.status();
    }

}
