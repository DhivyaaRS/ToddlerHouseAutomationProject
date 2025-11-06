package ts_003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import ts_002.TC_033;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Date;

public class TC_034 {
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

            // Step 4: Locate logo in header
            WebElement logo = driver.findElement(By.xpath("//header//img"));

            // Step 5: Check logo visibility and image source validity
            boolean isLogoVisible = logo.isDisplayed();
            String logoSrc = logo.getAttribute("src");

            boolean isLogoIntact = false;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(logoSrc).openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();
                int responseCode = connection.getResponseCode();
                isLogoIntact = (responseCode == 200);
            } catch (Exception checkEx) {
                isLogoIntact = false;
            }

            // Step 6: Click logo and verify redirection to homepage
            logo.click();
            Thread.sleep(3000); // Replace with WebDriverWait for production use
            String currentUrl = driver.getCurrentUrl();
            boolean isRedirectedToHome = currentUrl.equalsIgnoreCase("https://thetoddlerhouse.in/");

            // Final validation
            if (isLogoVisible && isLogoIntact && isRedirectedToHome) {
                System.out.println("✅ Logo is visible, intact, and redirects to homepage.");
            } else {
                System.out.println("❌ Logo validation failed. Visible: " + isLogoVisible +
                        ", Intact: " + isLogoIntact + ", Redirected: " + isRedirectedToHome);
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_034.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as logo_visibility_failure.png");
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
