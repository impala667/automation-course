import base.BaseTest;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestestHomePageVisualTest extends BaseTest {
    @Test
    void compareScreenshots() throws IOException {
        page.navigate("https://the-internet.herokuapp.com/");
        Path actualPath = Paths.get("target/screenshots/actual.png");
        Path expectedPath = Paths.get("src/test/resources/expected.png");
        Path diffPath = Paths.get("target/screenshots/diff.png");
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(actualPath));

        long mismatchPosition;
        try {
            mismatchPosition = Files.mismatch(actualPath, expectedPath);
            assertEquals(-1, mismatchPosition, "Файлы должны быть идентичны");
        } catch (AssertionError e) {
            BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedPath.toString());
            BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualPath.toString());

            ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage, diffPath.toFile());
            ImageComparisonResult result = imageComparison.compareImages();
            BufferedImage diffImage = result.getResult();
            ImageComparisonUtil.saveImage(diffPath.toFile(), diffImage);

            float diffPercent = result.getDifferencePercent();
            System.out.println("Процент отличающихся пикселей: " + diffPercent);
            throw new AssertionError("Скриншоты различаются. Процент различий: " + diffPercent +
                    "% . Diff-файл сохранён в: " + diffPath.toAbsolutePath(), e);
        }

    }

};
