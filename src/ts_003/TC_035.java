package ts_003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_035 {
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

            // Step 4: Locate "Home" navigation link
            WebElement homeLink = driver.findElement(By.linkText("Home"));

            // Step 5: Check visibility, readability, and alignment
            boolean isVisible = homeLink.isDisplayed();
            boolean isEnabled = homeLink.isEnabled();
            String linkText = homeLink.getText();
            Point location = homeLink.getLocation();
            Dimension size = homeLink.getSize();

            boolean isReadable = !linkText.trim().isEmpty();
            boolean isProperlyAligned = location.getY() >= 0 && size.getHeight() > 0;

            // Step 6: Click the "Home" link and verify redirection
            homeLink.click();
            Thread.sleep(3000); // Replace with WebDriverWait for production use
            String currentUrl = driver.getCurrentUrl();
            boolean isRedirected = currentUrl.equalsIgnoreCase("https://thetoddlerhouse.in/");

            // Final validation
            if (isVisible && isEnabled && isReadable && isProperlyAligned && isRedirected) {
                System.out.println("✅ 'Home' nav link is visible, readable, aligned, and redirects to homepage.");
            } else {
                System.out.println("❌ 'Home' nav link validation failed. Visible: " + isVisible +
                        ", Readable: " + isReadable + ", Aligned: " + isProperlyAligned +
                        ", Redirected: " + isRedirected);
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_035.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as home_nav_link_failure.png");
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

