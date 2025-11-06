package ts_001;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_006 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\dhivy\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 1: Launch URL
            driver.get("https://thetoddlerhouse.in");

            // Step 2: Locate banner section
            WebElement banner = driver.findElement(By.cssSelector("div.nav-header")); // Adjust selector as needed

            // Step 3: Resize to desktop
            driver.manage().window().setSize(new Dimension(1200, 800));
            Thread.sleep(1000);
            validateBanner(banner, "desktop");

            // Step 4: Resize to tablet
            driver.manage().window().setSize(new Dimension(768, 1024));
            Thread.sleep(1000);
            validateBanner(banner, "tablet");

            // Step 5: Resize to mobile
            driver.manage().window().setSize(new Dimension(375, 667));
            Thread.sleep(1000);
            validateBanner(banner, "mobile");

        } catch (Exception e) {
            System.out.println("⚠️ Test failed: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_")+"_";
    		String claName = TC_006.class.getSimpleName();
    		
            // Capture screenshot
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot captured: banner_responsive_failure.png");
                } catch (IOException ioException) {
                    System.out.println("❌ Failed to save screenshot: " + ioException.getMessage());
                }
            }

        } finally {
            // Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed.");
            }
        }
    }

    // Helper method to validate banner visibility and layout
    private static void validateBanner(WebElement banner, String deviceType) throws Exception {
        if (banner.isDisplayed() && banner.getSize().getWidth() > 0) {
            System.out.println("✅ Banner is responsive on " + deviceType + " view.");
        } else {
            throw new Exception("❌ Banner not properly displayed on " + deviceType + " view.");
        }
    }
}
