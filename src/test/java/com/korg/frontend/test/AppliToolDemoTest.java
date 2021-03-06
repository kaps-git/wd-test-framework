package com.korg.frontend.test;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.applitools.eyes.Eyes;
import com.korg.common.webdriver.WebDriverFactory;
import com.korg.common.webdriver.WebDriverManager;

public class AppliToolDemoTest {
	
	public HashMap<String,String> getChromeBrowserConfiguration() {
		HashMap<String,String> confMap = new HashMap<String, String>();
		
		confMap.put(WebDriverManager.BROWSER, "CHROME");
		confMap.put(WebDriverManager.CHROME_DRIVER_PATH, "/Users/akapil/mysoftwares/chromedriver");
		confMap.put(WebDriverManager.CHROME_BROWSER_PATH, "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome");
		
		return confMap;
	}
	
	@Test
	public void AabacoHomePageVisualTest() throws Exception {
		HashMap<String,String> confMap = new HashMap<String, String>();
		
		confMap.put(WebDriverManager.BROWSER, "CHROME");
		confMap.put(WebDriverManager.CHROME_DRIVER_PATH, "/Users/akapil/mysoftwares/chromedriver");
		confMap.put(WebDriverManager.CHROME_BROWSER_PATH, "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome");
		
		WebDriverManager wdManager = new WebDriverManager();
		wdManager.initDriver(confMap);
		
		WebDriverFactory.maximizeBrowser();
		WebDriver driver = WebDriverFactory.getDriver(); 
		
		Eyes eyes = new Eyes();
		eyes.setApiKey("mN9tVtYn4EX108htgwja3uUKhu4HuDSoLot3UVHpGsMD8110");
		
		driver = eyes.open(driver, "Aabaco Test", "Aabaco Home page visual test");
		eyes.setForceFullPageScreenshot(true);
		driver.get("https://www.aabacosmallbusiness.com/");
		driver.manage().window().maximize();
		Thread.sleep(1000);
		
		eyes.checkWindow("Aabaco Home Page");
		
		eyes.close();
		
		driver.close();
		
	}//end test
	
	
	@Test
	public void AabacoWebHostingOverviewPageVisualTest() throws Exception {
		
		
		WebDriverManager wdManager = new WebDriverManager();
		wdManager.initDriver(getChromeBrowserConfiguration());
		
		WebDriverFactory.maximizeBrowser();
		WebDriver driver = WebDriverFactory.getDriver(); 
		
		Eyes eyes = new Eyes();
		eyes.setApiKey("mN9tVtYn4EX108htgwja3uUKhu4HuDSoLot3UVHpGsMD8110");
		
		driver = eyes.open(driver, "Aabaco Test", "Web Hosting Overview page");
		eyes.setForceFullPageScreenshot(true);
		driver.get("https://www.aabacosmallbusiness.com/webhosting");
		driver.manage().window().maximize();
		Thread.sleep(1000);
		
		eyes.checkWindow("Aabaco Home Page");
		
		eyes.close();
		
		driver.close();
		
	}//end test

}
