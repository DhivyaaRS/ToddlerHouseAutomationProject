package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_017 {

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

            // Step 3: Click on "Forgot your password?" link
            WebElement forgotPasswordLink = driver.findElement(By.xpath("//p[contains(text(),'Forgot your password?')]")); // Adjust text if needed
            forgotPasswordLink.click();

            // Step 4: Observe response - check for redirection or modal
            Thread.sleep(3000); // Wait for response
            String currentUrl = driver.getCurrentUrl();
            boolean isResetModalVisible = false;

            try {
                WebElement resetModal = driver.findElement(By.xpath("//h2[contains(text(),'Reset your password')]")); // Adjust selector if modal is used
                isResetModalVisible = resetModal.isDisplayed();
            } catch (NoSuchElementException ignored) {}

            if (currentUrl.contains("password") || isResetModalVisible) {
                System.out.println("✅ Password recovery page or reset modal is displayed.");
            } else {
                throw new Exception("❌ No password recovery page or modal appeared.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test failed: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_017.class.getSimpleName();
    		
            // Capture screenshot
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot captured: forgot_password_failure.png");
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

