package ts_004;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_053 {
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

            // Step 4: Locate search bar and icon
            WebElement searchInput = driver.findElement(By.xpath("//input[@type='search' or contains(@placeholder,'Search')]"));
            WebElement searchIcon = driver.findElement(By.xpath("//button[contains(@class,'search') or contains(text(),'Search')]"));

            // Check visibility and alignment
            boolean isSearchBarVisible = searchInput.isDisplayed();
            boolean isSearchIconVisible = searchIcon.isDisplayed();

            Point inputLocation = searchInput.getLocation();
            Point iconLocation = searchIcon.getLocation();

            boolean isHorizontallyAligned = Math.abs(inputLocation.getY() - iconLocation.getY()) <= 5;

            if (isSearchBarVisible && isSearchIconVisible && isHorizontallyAligned) {
                System.out.println("✅ Search bar and icon are properly aligned and visually consistent.");
            } else {
                System.out.println("❌ Search bar alignment or visibility issue. Bar visible: " + isSearchBarVisible +
                        ", Icon visible: " + isSearchIconVisible + ", Aligned: " + isHorizontallyAligned);
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed due to exception: " + e.getMessage());

            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_");
    		String claName = TC_053.class.getSimpleName();
    		
            // Capture screenshot on failure
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot saved as search_bar_alignment_failure.png");
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
