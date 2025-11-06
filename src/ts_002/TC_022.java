package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_022 {

    public static void main(String[] args) {
        WebDriver driver = null;

        // Define viewport sizes for desktop, tablet, and mobile
        Dimension[] viewports = {
            new Dimension(1920, 1080), // Desktop
            new Dimension(768, 1024),  // Tablet
            new Dimension(375, 667)    // Mobile
        };

        try {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\dhivy\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            for (Dimension viewport : viewports) {
                driver.manage().window().setSize(viewport);
                driver.get("https://thetoddlerhouse.in/");

                // Click on "Account" in top banner
                WebElement accountLink = driver.findElement(By.xpath("//span[contains(text(),'Account')]"));
                accountLink.click();

                // Wait for modal to appear
                WebElement modal = driver.findElement(By.id("user-notification")); // Adjust selector if needed

                // Validate modal is within viewport
                Dimension modalSize = modal.getSize();
                Dimension windowSize = driver.manage().window().getSize();

                boolean isResponsive = modalSize.getWidth() <= windowSize.getWidth() &&
                                       modalSize.getHeight() <= windowSize.getHeight();

                System.out.println("Viewport: " + viewport.getWidth() + "x" + viewport.getHeight());
                if (isResponsive) {
                    System.out.println("✅ Modal is responsive and fits within the screen.");
                } else {
                    System.out.println("❌ Modal overflows the screen.");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_")+"_";
    		String claName = TC_022.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as modal_responsiveness_failure.png");
                } catch (IOException ioException) {
                    System.out.println("⚠️ Failed to save screenshot: " + ioException.getMessage());
                }
            }

        } finally {
            // Ensure browser is closed
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed.");
            }
        }
    }
}
