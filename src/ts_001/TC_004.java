package ts_001;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_004 {

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

            // Step 2: Click on "Wishlist" link
            WebElement wishlistLink = driver.findElement(By.linkText("Wishlist")); // Adjust locator if needed
            wishlistLink.click();

            // Step 3: Verify URL contains "/wishlist"
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("/wishlist")) {
                throw new Exception("❌ URL does not reflect Wishlist page. Found: " + currentUrl);
            }

            // Step 4: Verify Wishlist page is empty
            WebElement emptyMessage = driver.findElement(By.xpath("//*[contains(text(),'Your wishlist is empty')]")); // Adjust text if needed
            if (emptyMessage.isDisplayed()) {
                System.out.println("✅ Wishlist page is empty and URL is correct: " + currentUrl);
            } else {
                throw new Exception("❌ Wishlist page is not empty or message not found.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test failed: " + e.getMessage());
            
            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_")+"_";
    		String claName = TC_004.class.getSimpleName();
    				
            // Capture screenshot
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot captured: wishlist_page_failure.png");
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
}

