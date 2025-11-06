package ts_003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_044 {
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

            // Step 4: Locate "Shopping Cart" button
            WebElement cartButton = driver.findElement(By.id("_desktop_cart"));

            // Step 5: Hover over the "Shopping Cart" button
            Actions actions = new Actions(driver);
            actions.moveToElement(cartButton).perform();
            Thread.sleep(1000); // Allow hover effect to render

            // Step 6: Check for icon or message visibility
            boolean hoverEffectVisible = false;

            try {
                WebElement hoverMessage = driver.findElement(By.xpath("//*[contains(text(),'Cart') or contains(@class,'cart-icon')]"));
                hoverEffectVisible = hoverMessage.isDisplayed();
            } catch (NoSuchElementException ignored) {
                hoverEffectVisible = false;
            }

            // Final validation
            if (hoverEffectVisible) {
                System.out.println("✅ Hovering over 'Shopping Cart' button displays icon or message.");
            } else {
                System.out.println("❌ Hovering over 'Shopping Cart' button does NOT display icon or message.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_044.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as shopping_cart_hover_failure.png");
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

