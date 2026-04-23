package utils;

import org.openqa.selenium.*;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtils {

    public static String capture(WebDriver driver, String name) {

        String path = System.getProperty("user.dir") + "/screenshots/"
                + name + "_" + System.currentTimeMillis() + ".png";

        try {
            File dir = new File(System.getProperty("user.dir") + "/screenshots");
            if (!dir.exists()) {
                dir.mkdirs(); // ✅ create folder if not exists
            }

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(path));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }
}