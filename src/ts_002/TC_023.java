package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_023 {
    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Setup WebDriver
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\dhivy\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 1 & 2: Launch URL
            driver.get("https://thetoddlerhouse.in/");

            // Step 3: Observe banner section (implicitly done)
            // Step 4: Click on "Account" in top banner
            WebElement accountLink = driver.findElement(By.xpath("//span[contains(text(),'Account')]"));
            accountLink.click();

            // Step 5: Enter 256+ characters in email and password fields
            String longInput = "a".repeat(260) + "@test.com";
            String longPassword = "p".repeat(260);

            WebElement emailField = driver.findElement(By.id("CustomerEmail"));
            WebElement passwordField = driver.findElement(By.id("CustomerPassword"));

            emailField.sendKeys(longInput);
            passwordField.sendKeys(longPassword);

            // Validate input length is trimmed or restricted
            int emailLength = emailField.getAttribute("value").length();
            int passwordLength = passwordField.getAttribute("value").length();

            System.out.println("Entered Email Length: " + emailLength);
            System.out.println("Entered Password Length: " + passwordLength);

            if (emailLength <= 255 && passwordLength <= 255) {
                System.out.println("✅ Input is limited appropriately (≤ 255 characters).");
            } else {
                System.out.println("❌ Input exceeds expected limit.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_023.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as max_char_limit_failure.png");
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
