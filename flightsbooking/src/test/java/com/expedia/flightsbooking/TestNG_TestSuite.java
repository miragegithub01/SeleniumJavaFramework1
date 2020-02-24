package com.expedia.flightsbooking;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageclasses.SearchPage;

public class TestNG_TestSuite {
	private WebDriver driver;
	private String baseUrl;
	static Logger log = Logger.getLogger(TestNG_TestSuite.class);

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		//driver = new ChromeDriver();
		baseUrl = "https://www.expedia.com/";

		// Maximize the browser's window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		PropertyConfigurator.configure("log4j.properties");
		driver.get(baseUrl);
	}

	@Test
	public void test1_fillBasicInfo() throws Exception {
		SearchPage.navigateToFlightsTab(driver);
		SearchPage.fillOriginTextBox(driver, "Atlanta");
		SearchPage.fillDestinationTextBox(driver, "Chicago");
		SearchPage.fillDepartureDateTextBox(driver, "2/16/2020");
		SearchPage.fillReturnDateTextBox(driver, "2/20/2020");
		Thread.sleep(3000);
	}
	
	@Test
	public void test2_fillAdvancedInfo() {
		// Expedia remembers last settings, if you have previously visited the page clicked advanced options
		// Then next time, when automation runs that option will already be open
		// And automation will click it and this time it will close
		// And the test will faill because it will not be able to find not-stop and flight class checkboxes
		// We are adding If condition to find whether the advanced option is open or close
		WebElement e = driver.findElement(By.id("flight-advanced-options-hp-flight"));
		String clicked = e.getAttribute("name");
		System.out.println("Value of name is: " + clicked);
		if (clicked.contains("false")) {
			SearchPage.clickOnAdvancedLink(driver);
		}
		SearchPage.clickNonStopCheckBox(driver);
		SearchPage.selectFlightClass(driver, "first");
	}

	@AfterClass
	public void afterClass() {
	}

}
