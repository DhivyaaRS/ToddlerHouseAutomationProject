package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_018 {

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

            // Step 3: Click on "Create account" link
            WebElement createAccountLink = driver.findElement(By.xpath("//p[contains(text(),'Create account')]")); // Adjust text if needed
            createAccountLink.click();
            
            WebElement createAccountAnotherLink = driver.findElement(By.xpath("//button[contains(text(),'Create')]")); // Adjust text if needed
            createAccountAnotherLink.click();

            // Step 4: Observe redirection and error message
            Thread.sleep(3000); // Wait for page to load
            String currentUrl = driver.getCurrentUrl();
            boolean errorVisible = false;

            try {
                WebElement errorMessage = driver.findElement(By.xpath("//*[contains(text(),'Something went wrong.')]")); // Adjust text or selector
                errorVisible = errorMessage.isDisplayed();
            } catch (NoSuchElementException ignored) {}
            

            if (currentUrl.contains("account") || errorVisible) {
                System.out.println("✅ Redirected to registration page and error message is displayed.");
            } else {
                throw new Exception("❌ Either redirection failed or error message not displayed.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test failed: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_018.class.getSimpleName();
    		
            // Capture screenshot
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot captured: create_account_failure.png");
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

