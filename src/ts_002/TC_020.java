package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_020 {
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

            // Step 5: Use Tab key to move through fields and validate focus order
            Actions actions = new Actions(driver);
            WebElement emailField = driver.switchTo().activeElement();
            actions.sendKeys(Keys.TAB).perform();
            WebElement passwordField = driver.switchTo().activeElement();
            actions.sendKeys(Keys.TAB).perform();
            WebElement forgotLink = driver.switchTo().activeElement();
            actions.sendKeys(Keys.TAB).perform();
            WebElement signInButton = driver.switchTo().activeElement();
            actions.sendKeys(Keys.TAB).perform();
            WebElement createAccountLink = driver.switchTo().activeElement();

            // Validate logical tab order
            boolean isOrderCorrect =
                    emailField.getAttribute("name").equalsIgnoreCase("customer[email]") &&
                    passwordField.getAttribute("name").equalsIgnoreCase("customer[password]") &&
                    forgotLink.getText().contains("Forgot") &&
                    signInButton.getAttribute("value").equalsIgnoreCase("Sign In") &&
                    createAccountLink.getText().contains("Create Account");

            if (isOrderCorrect) {
                System.out.println("✅ Tab navigation order is logical.");
            } else {
                System.out.println("❌ Tab navigation order is NOT logical.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_020.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as tab_navigation_failure.png");
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

