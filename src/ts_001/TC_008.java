package ts_001;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_008 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Launch incognito browser (simulate new user)
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\dhivy\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");
            ChromeOptions incognitoOptions = new ChromeOptions();
            incognitoOptions.addArguments("--incognito");
            incognitoOptions.addArguments("--start-maximized");
            driver = new ChromeDriver(incognitoOptions);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Open homepage
            driver.get("https://thetoddlerhouse.in");

            // Step 3: Check banner text for new user
            WebElement bannerText = driver.findElement(By.xpath("//*[contains(text(),'Get 10% OFF On Your FIRST Order')]")); // Adjust if needed
            if (bannerText.isDisplayed()) {
                System.out.println("✅ Banner text is visible for new user.");
            } else {
                throw new Exception("❌ Banner text not visible for new user.");
            }

            // Step 4: Simulate returning user by restarting browser (without incognito)
            driver.quit();
            ChromeOptions regularOptions = new ChromeOptions();
            regularOptions.addArguments("--start-maximized");
            driver = new ChromeDriver(regularOptions);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 5: Open homepage again
            driver.get("https://thetoddlerhouse.in");

            // Step 6: Check if banner text is hidden for returning user
            boolean bannerVisible;
            try {
                bannerText = driver.findElement(By.xpath("//*[contains(text(),'Get 10% OFF On Your FIRST Order')]"));
                bannerVisible = bannerText.isDisplayed();
            } catch (NoSuchElementException e) {
                bannerVisible = false;
            }

            if (!bannerVisible) {
                System.out.println("✅ Banner text is hidden for returning user.");
            } else {
                throw new Exception("❌ Banner text still visible for returning user.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test failed: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_")+"_";
    		String claName = TC_008.class.getSimpleName();
    		
            // Capture screenshot
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot captured: banner_new_user_test_failure.png");
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
