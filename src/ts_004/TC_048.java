package ts_004;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_048 {
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

            // Step 3: Click on Search button
            WebElement searchButton = driver.findElement(By.id("_desktop_search"));
            searchButton.click();
            Thread.sleep(2000); // Allow search bar to appear

            // Step 4: Enter invalid keyword
            WebElement searchInput = driver.findElement(By.xpath("//input[@type='search' or contains(@placeholder,'Search')]"));
            searchInput.sendKeys("12345");

            // Step 5: Click the search icon
            WebElement searchIcon = driver.findElement(By.xpath("//button[contains(@class,'search') or contains(text(),'Search')]"));
            searchIcon.click();
            Thread.sleep(3000); // Wait for results to load

            // Step 6: Verify "No Products found" message
            boolean noResultsMessageVisible = false;
            try {
                WebElement noResultsMessage = driver.findElement(By.xpath("//div[@class=\"title alert-danger alert\" or contains(text(),'No Products found') or contains(text(),'No results')]"));
                noResultsMessageVisible = noResultsMessage.isDisplayed();
            } catch (NoSuchElementException ignored) {
                noResultsMessageVisible = false;
            }

            // Final validation
            if (noResultsMessageVisible) {
                System.out.println("✅ 'No Products found' message is displayed for invalid keyword.");
            } else {
                System.out.println("❌ No message displayed for invalid keyword search.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_048.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as invalid_search_failure.png");
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
