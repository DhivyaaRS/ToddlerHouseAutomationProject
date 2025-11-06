package ts_004;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_045 {
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
            WebElement searchButton = driver.findElement(By.id("_desktop_cart"));
            searchButton.click();
            Thread.sleep(5000); // Allow search bar to appear

            // Step 4: Verify search bar, icon, and placeholder
            WebElement searchInput = driver.findElement(By.xpath("//input[@type='search' or contains(@placeholder,'Search')]"));
            boolean isSearchBarVisible = searchInput.isDisplayed();
            String placeholderText = searchInput.getAttribute("placeholder");

            boolean isPlaceholderCorrect = placeholderText != null && placeholderText.toLowerCase().contains("search");

            // Optional: Check for search icon (if present as separate element)
            boolean isSearchIconVisible = false;
            try {
                WebElement searchIcon = driver.findElement(By.xpath("//*[contains(@class,'search-icon') or contains(@class,'fa-search')]"));
                isSearchIconVisible = searchIcon.isDisplayed();
            } catch (NoSuchElementException ignored) {
                isSearchIconVisible = false;
            }

            // Final validation
            if (isSearchBarVisible && isPlaceholderCorrect && isSearchIconVisible) {
                System.out.println("✅ Search bar, icon, and placeholder are displayed correctly.");
            } else {
                System.out.println("❌ Search bar validation failed. Bar: " + isSearchBarVisible +
                        ", Placeholder: " + isPlaceholderCorrect + ", Icon: " + isSearchIconVisible);
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_045.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as search_bar_display_failure.png");
                } catch (IOException ioException) {
                    System.out.println("⚠️ Failed to save screenshot: " + ioException.getMessage());
                }
            }

        } finally {
            // Step 5: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed.");
            }
        }
    }
}
