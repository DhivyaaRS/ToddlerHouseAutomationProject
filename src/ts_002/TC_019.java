package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_019 {

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

            // Step 2: Click on "Account" in top banner
            WebElement accountLink = driver.findElement(By.xpath("//span[contains(text(),'Account')]")); // Adjust locator if needed
            accountLink.click();

            // Step 3: Locate Sign In button
            WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")); // Adjust XPath if needed

            System.out.println(signInButton.isEnabled()); //false
            
            // Step 4: Check if button is disabled when fields are blank
            boolean isDisabledInitially = !signInButton.isEnabled();
            if (isDisabledInitially) {
                System.out.println("✅ Sign In button is disabled when fields are blank.");
            } else {
                throw new Exception("❌ Sign In button is enabled even when fields are blank.");
            }

            // Step 5: Fill valid inputs
            WebElement emailField = driver.findElement(By.id("CustomerEmail")); // Adjust ID if needed
            WebElement passwordField = driver.findElement(By.id("CustomerPassword")); // Adjust ID if needed
            emailField.sendKeys("test@example.com");
            passwordField.sendKeys("ValidPassword123");

            // Step 6: Check if button is enabled
            Thread.sleep(1000); // Allow UI to update
            boolean isEnabledAfterInput = signInButton.isEnabled();
            if (isEnabledAfterInput) {
                System.out.println("✅ Sign In button is enabled after entering valid inputs.");
            } else {
                throw new Exception("❌ Sign In button is still disabled after entering valid inputs.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test failed: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_019.class.getSimpleName();
    		
            // Capture screenshot
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot captured: signin_button_state_failure.png");
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
