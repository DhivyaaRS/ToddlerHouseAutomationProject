package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_031 {
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

            // Step 5: Locate Sign In button
            WebElement signInButton = driver.findElement(By.xpath("//p[contains(text(),'Create account')]")); // Adjust XPath if needed
            signInButton.click();
            
            // Step 6: Click “Already Have an Account?”
            WebElement alreadyHaveAccountLink = driver.findElement(By.xpath("//p[contains(text(),'Already Have an Account ?')]"));
            alreadyHaveAccountLink.click();

            // Step 7: Verify redirection to login page
            Thread.sleep(3000); // Replace with WebDriverWait for production use
            String currentUrl = driver.getCurrentUrl();
            boolean isLoginPage = currentUrl.toLowerCase().contains("login");

            if (isLoginPage) {
                System.out.println("✅ Successfully redirected to Login page.");
            } else {
                System.out.println("❌ Redirection to Login page failed.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_031.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as already_have_account_navigation_failure.png");
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

