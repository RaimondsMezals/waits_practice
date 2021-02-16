package qa.com.waits.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class WaitsPracticeTest {
	private static RemoteWebDriver driver;
	private static WebElement targ;
	private static ExtentReports report;
	private static ExtentTest test;

	@BeforeAll
	public static void setUp() {

		report = new ExtentReports(
				"C:\\Users\\raimo\\Desktop\\EclipseWorkspace\\waits_practice\\target\\reports\\extentreports\\waitReport.html",
				true);

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

		driver = new ChromeDriver();
		System.out.println("Tests have Started");

	}

	@AfterAll
	public static void cleanUp() {
		driver.quit();
		System.out.println("The driver has been closed!");
	}

	@BeforeEach
	public void before() {
		System.out.println("\nTest has started!");
	}

	@AfterEach
	public void after() {

		report.endTest(test);
		report.flush();
		report.close();

		System.out.println("\nTest has finished!");
	}

	@Test
	public void generateProfileTest() {

		test = report.startTest("Generate Profile Test");

		// given
		test.log(LogStatus.INFO, "Given - we can access seleniumeasy.com");
		driver.get("https://www.seleniumeasy.com/test/dynamic-data-loading-demo.html");

		// when i click new user button
		test.log(LogStatus.INFO, "When - i click new user button");
		targ = driver.findElement(By.xpath("//*[@id=\"save\"]"));
		targ.click();
		try {
			new WebDriverWait(driver, 3)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"loading\"]/br[2]")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		targ = driver.findElement(By.id("loading"));

		// then i should see a result of a random user
		test.log(LogStatus.INFO, "Then - I should see a result of a random user");
		String stringResult = targ.getText();

		System.out.println(stringResult);

		boolean result = stringResult.contains("First Name :") && stringResult.contains("First Name :");

		if (result) {
			test.log(LogStatus.PASS, "Found first and last name!");
		} else {
			test.log(LogStatus.FAIL, "Fount: " + stringResult);
		}

		if (stringResult.equals("loading...")) {
			test.log(LogStatus.FAIL, "Element not populated");
		} else {
			test.log(LogStatus.PASS, "Loaded details successfully!");
		}

		assertNotEquals("loading...", stringResult);
		assertTrue(result);

	}
}
