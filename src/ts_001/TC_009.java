package ts_001;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_009 {

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

            // Step 2: Locate banner element
            WebElement banner = driver.findElement(By.cssSelector("div.nav-header")); // Adjust selector as needed

            // Step 3: Check for screen reader accessibility (ARIA attributes)
            String ariaLabel = banner.getAttribute("aria-label");
            String role = banner.getAttribute("role");

            if ((ariaLabel != null && !ariaLabel.isEmpty()) || (role != null && !role.isEmpty())) {
                System.out.println("✅ Banner has ARIA attributes for screen reader accessibility.");
            } else {
                throw new Exception("❌ Banner lacks ARIA attributes.");
            }

            // Step 4: Check contrast ratio (basic approximation)
            String bgColor = banner.getCssValue("background-color");
            WebElement textElement = banner.findElement(By.cssSelector("div.nav-header")); // Adjust if needed
            String textColor = textElement.getCssValue("color");

            double contrastRatio = calculateContrastRatio(bgColor, textColor);
            if (contrastRatio >= 4.5) {
                System.out.println("✅ Contrast ratio is sufficient: " + contrastRatio);
            } else {
                throw new Exception("❌ Contrast ratio too low: " + contrastRatio);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Test failed: " + e.getMessage());
            
            Date d = new Date();
    		String newDate = d.toString().replace(" ", "_").replace(":", "_")+"_";
    		String claName = TC_009.class.getSimpleName();
    		
            // Capture screenshot
            if (driver != null) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(screenshot, new File("./errorShots/"+newDate+"_"+claName+".png"));
                    System.out.println("📸 Screenshot captured: banner_accessibility_failure.png");
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

    // Helper method to calculate contrast ratio (simplified)
    private static double calculateContrastRatio(String bgColorCss, String textColorCss) {
        Color bg = parseColor(bgColorCss);
        Color text = parseColor(textColorCss);

        double bgLuminance = getLuminance(bg);
        double textLuminance = getLuminance(text);

        return (Math.max(bgLuminance, textLuminance) + 0.05) / (Math.min(bgLuminance, textLuminance) + 0.05);
    }

    private static Color parseColor(String cssColor) {
        String[] rgba = cssColor.replace("rgba(", "").replace("rgb(", "").replace(")", "").split(",");
        return new Color(Integer.parseInt(rgba[0].trim()), Integer.parseInt(rgba[1].trim()), Integer.parseInt(rgba[2].trim()));
    }

    private static double getLuminance(Color color) {
        double r = color.getRed() / 255.0;
        double g = color.getGreen() / 255.0;
        double b = color.getBlue() / 255.0;

        r = (r <= 0.03928) ? r / 12.92 : Math.pow((r + 0.055) / 1.055, 2.4);
        g = (g <= 0.03928) ? g / 12.92 : Math.pow((g + 0.055) / 1.055, 2.4);
        b = (b <= 0.03928) ? b / 12.92 : Math.pow((b + 0.055) / 1.055, 2.4);

        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }
}
