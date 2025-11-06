package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_015 {

    public static void main(String[] args) {
        WebDriver driver = null;

        // Replace with a valid email and intentionally incorrect password
        String validEmail = "dhivyaa.rs@gmail.com";
        String invalidPassword = "abc@123";

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

            // Step 3: Enter valid email and incorrect password
            WebElement emailField = driver.findElement(By.id("CustomerEmail")); // Adjust ID if needed
            WebElement passwordField = driver.findElement(By.id("CustomerPassword")); // Adjust ID if needed
            emailField.sendKeys(validEmail);
            passwordField.sendKeys(invalidPassword);

            // Step 4: Click "Sign In"
            WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")); // Adjust XPath
            signInButton.click();

            // Step 5: Check for error message
            Thread.sleep(3000); // Wait for error to appear
            WebElement errorMessage = driver.findElement(By.xpath("//*[contains(text(),'Invalid email or password')]")); // Adjust text or selector
            if (errorMessage.isDisplayed()) {
                System.out.println("✅ Error message displayed for invalid login attempt.");
            } else {
                throw new Exception("❌ Error message not displayed for invalid login attempt.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test failed: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_015.class.getSimpleName();
    		
            // Capture screenshot
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot captured: invalid_login_failure.png");
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

