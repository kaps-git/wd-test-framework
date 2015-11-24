package com.korg.common.webdriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

public class WebDriverUtil {

	/**
	 * 
	 * @param locator
	 * @param waitTime
	 * @param pollInterval
	 * @return
	 * @throws Exception
	 */
	public WebElement waitForElement(final By locator, long waitTime, long pollInterval) throws Exception {
		   FluentWait<WebDriver> wait = new FluentWait<WebDriver>(WebDriverManager.getDriver())
		           .withTimeout(waitTime, TimeUnit.SECONDS)
		           .pollingEvery(pollInterval, TimeUnit.SECONDS)
		           .ignoring(NoSuchElementException.class);

		   WebElement element = wait.until(new Function<WebDriver, WebElement>() {
		       public WebElement apply(WebDriver driver) {
		           return driver.findElement(locator);
		       }
		   });

		   return  element;
	}//end func
	
	
	 public void CaptureSnapShot(String fileName) throws Exception {
	    	
	    		File screenshotFile = null;
	    		
	    		if(WebDriverManager.getDriver().getClass().getName().contains("RemoteWebDriver")) {
	    			WebDriver augmentedDriver = new Augmenter().augment(WebDriverManager.getDriver());
	    			screenshotFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);    		
	    		} else {
	    			screenshotFile = ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
	    		}
	    		
	    		File destFile = new File(fileName);
	    		destFile.createNewFile();
	    		FileUtils.copyFile(screenshotFile, new File(fileName)); 
	  }//end func
	
}
