import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.nio.file.Files;

import base.BaseTestApi;

public class FileUploadTest extends BaseTestApi {
    @Test
    void testFileUploadAndDownload() throws Exception {
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setColor(Color.PINK);
            g.fillRect(0, 0, 200, 200);
        } finally {
            g.dispose();
        }
        byte[] originalBytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            originalBytes = baos.toByteArray();
        }
        FilePayload filePayload = new FilePayload("test_image.png", "image/png", originalBytes);
        response = requestContext.post(
                "/post",
                RequestOptions.create().setMultipart(
                        FormData.create().set("test_file", filePayload)));
        assertTrue(response.ok());
        String responseText = response.text();
        assertTrue(responseText.contains("data:image/png;base64"));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseText);
        String dataUrl = root.path("files").path("test_file").asText();
        String base64Data = dataUrl.split(",", 2)[1];
        byte[] recivedBytes = Base64.getDecoder().decode(base64Data);
        assertArrayEquals(originalBytes, recivedBytes);

        APIResponse downloadResponse = requestContext.get("https://httpbin.org/image/png");
        assertTrue(downloadResponse.ok());
        byte[] content = downloadResponse.body();
        Files.write(Paths.get("target/downloaded_image.png"), content);
        String mimeType = Files.probeContentType(Paths.get("target/downloaded_image.png"));
        assertNotNull(mimeType);
        assertEquals("image/png", mimeType);
        byte[] pngSignature = {
                (byte) 137, (byte) 80, (byte) 78, (byte) 71,
                (byte) 13, (byte) 10, (byte) 26, (byte) 10
        };
        assertTrue(content.length >= 8);
        for (int i = 0; i < 8; i++) {
            assertEquals(content[i], pngSignature[i]);
        }
    }
}
