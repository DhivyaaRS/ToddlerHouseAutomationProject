package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_025 {
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

            // Fill in valid inputs
            driver.findElement(By.name("customer[first_name]")).sendKeys("Dhivya");
            driver.findElement(By.name("customer[last_name]")).sendKeys("AAA");
            driver.findElement(By.id("RegisterForm-email")).sendKeys("dhivya.test@example.com");
            driver.findElement(By.id("RegisterForm-password")).sendKeys("SecurePass123");

            // Step 6: Click CREATE
            WebElement registerButton = driver.findElement(By.xpath("//button[contains(text(),'Create')]"));
            registerButton.click();

            // Wait and verify success message or redirection
            Thread.sleep(3000); // Replace with WebDriverWait for production use

            String currentUrl = driver.getCurrentUrl();
            boolean isSuccess = !currentUrl.contains("account") || driver.getPageSource().toLowerCase().contains("success");

            if (isSuccess) {
                System.out.println("✅ Account created successfully. User redirected or shown success message.");
            } else {
                System.out.println("❌ Account creation failed. No success message or redirection detected.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_025.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as create_account_submission_failure.png");
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

