import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.Test;
import base.BaseTestApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class TodoApiTest extends BaseTestApi {
    @Test
    void testTodoApi() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        response = requestContext.get("/posts/1");
        assertThat(response).isOK();
        assertEquals(200, response.status());
        String response_text = response.text();
        JsonNode root = objectMapper.readTree(response_text);
        assertTrue(root.has("id"));
        assertTrue(root.get("id").isInt());
        assertTrue(root.has("title"));
        assertTrue(root.get("title").isTextual());
        assertTrue(root.has("body"));
        assertTrue(root.get("body").isTextual());
        assertTrue(root.has("userId"));
        assertTrue(root.get("userId").isInt());
    }
}
