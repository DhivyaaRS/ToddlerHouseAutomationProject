package ts_002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class TC_024 {
	public static void main(String[] args) {
		WebDriver driver = null;

		try {
			// Step 1: Setup WebDriver
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\dhivy\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			// Step 2: Launch URL
			driver.get("https://thetoddlerhouse.in/");

			// Step 3: Observe banner section (implicitly done)

			// Step 4: Click on "Account" in the top banner
			WebElement accountLink = driver.findElement(By.xpath("//span[contains(text(),'Account')]"));
			accountLink.click();

			// Step 5: Navigate to "Create Account" page
			WebElement createAccountLink = driver.findElement(By.xpath("//p[contains(text(),'Create account')]"));
			createAccountLink.click();

			// Wait for form to load and verify key elements
			WebElement firstNameField = driver.findElement(By.name("customer[first_name]"));
			WebElement lastNameField = driver.findElement(By.name("customer[last_name]"));
			WebElement emailField = driver.findElement(By.name("customer[email]"));
			WebElement passwordField = driver.findElement(By.name("customer[password]"));
			WebElement registerButton = driver.findElement(By.xpath("//button[contains(text(),'Create')]"));

			boolean allElementsVisible = firstNameField.isDisplayed() && lastNameField.isDisplayed()
					&& emailField.isDisplayed() && passwordField.isDisplayed() && registerButton.isDisplayed();

			if (allElementsVisible) {
				System.out.println("✅ 'Create Account' form loaded successfully with all fields and buttons visible.");
			} else {
				System.out.println("❌ Some form elements are missing or not visible.");
			}

		} catch (Exception e) {
			System.out.println("❌ Test failed due to exception: " + e.getMessage());

			Date d = new Date();
			String newDate = d.toString().replace(" ", "_").replace(":", "_");
			String claName = TC_024.class.getSimpleName();

			// Capture screenshot on failure
			if (driver != null) {
				File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				try {
					FileHandler.copy(screenshot, new File("./errorShots/" + newDate + "_" + claName + ".png"));
					System.out.println("📸 Screenshot saved as create_account_form_failure.png");
				} catch (IOException ioException) {
					System.out.println("⚠️ Failed to save screenshot: " + ioException.getMessage());
				}
			}

		} finally {
			// Step 6: Close browser
			if (driver != null) {
				driver.quit();
				System.out.println("🧹 Browser closed.");
			}
		}
	}
}
