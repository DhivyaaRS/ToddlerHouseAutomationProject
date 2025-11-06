package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_026 {
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

            // Step 3: Observe banner section (implicitly done)

            // Step 4: Click on "Account" in the top banner
            WebElement accountLink = driver.findElement(By.xpath("//span[contains(text(),'Account')]"));
            accountLink.click();

            // Step 5: Navigate to "Create Account" page
            WebElement createAccountLink = driver.findElement(By.xpath("//p[contains(text(),'Create account')]"));
            createAccountLink.click();

            // Step 6: Leave all fields blank and click CREATE
            WebElement registerButton = driver.findElement(By.xpath("//button[contains(text(),'Create')]"));
            registerButton.click();

            // Step 7: Check for error messages
            boolean nameError = driver.getPageSource().toLowerCase().contains("name is required");
            boolean emailError = driver.getPageSource().toLowerCase().contains("email is required");
            boolean passwordError = driver.getPageSource().toLowerCase().contains("password is required");

            if (nameError && emailError && passwordError) {
                System.out.println("✅ Error messages appeared for all required fields.");
            } else {
                System.out.println("❌ Error messages did NOT appear for all required fields.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_026.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as empty_field_validation_failure.png");
                } catch (IOException ioException) {
                    System.out.println("⚠️ Failed to save screenshot: " + ioException.getMessage());
                }
            }

        } finally {
            // Step 8: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed.");
            }
        }
    }
}

