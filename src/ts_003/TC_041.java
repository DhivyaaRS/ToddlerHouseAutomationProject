package ts_003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_041 {
    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Setup WebDriver
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\dhivy\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Launch URL
            driver.get("https://thetoddlerhouse.in/");

            // Step 3: Observe header section (implicitly done)

            // Step 4: Locate "Search" button
            WebElement searchButton = driver.findElement(By.id("_desktop_search"));

            // Step 5: Check visibility, readability, and alignment
            boolean isVisible = searchButton.isDisplayed();
            boolean isEnabled = searchButton.isEnabled();
            String buttonText = searchButton.getText();
            Point location = searchButton.getLocation();
            Dimension size = searchButton.getSize();

            boolean isReadable = !buttonText.trim().isEmpty();
            boolean isProperlyAligned = location.getY() >= 0 && size.getHeight() > 0;

            // Step 6: Click the "Search" button and verify search field appears
            searchButton.click();
            Thread.sleep(2000); // Replace with WebDriverWait for production use

            WebElement searchInput = driver.findElement(By.xpath("//input[@type='search' or contains(@placeholder,'Search')]"));
            boolean isSearchFieldVisible = searchInput.isDisplayed();

            // Final validation
            if (isVisible && isEnabled && isReadable && isProperlyAligned && isSearchFieldVisible) {
                System.out.println("✅ 'Search' button is visible, readable, aligned, and activates search functionality.");
            } else {
                System.out.println("❌ 'Search' button validation failed. Visible: " + isVisible +
                        ", Readable: " + isReadable + ", Aligned: " + isProperlyAligned +
                        ", Search Field Visible: " + isSearchFieldVisible);
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_041.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as search_button_failure.png");
                } catch (IOException ioException) {
                    System.out.println("⚠️ Failed to save screenshot: " + ioException.getMessage());
                }
            }

        } finally {
            // Step 7: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed.");
            }
        }
    }
}
