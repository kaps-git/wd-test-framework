package com.korg.common.webdriver;

import java.awt.Toolkit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class WebDriverManager {
	
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<String> sessionId = new ThreadLocal<String>();	//For saucelabs

	 
    public static synchronized WebDriver getDriver() {
        return webDriver.get();
    }
 
    static synchronized void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    //For saucelabs
    static synchronized void setSessionID(String  sesID) {
    	sessionId.set(sesID);
    }
    
 
    /**
    *
    * @return the Sauce Job id for the current thread for saucelabs
    */
   static synchronized String getSessionId() {
       return sessionId.get();
   }

   public static synchronized void maximizeBrowser() throws Exception {
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 int Width = (int) toolkit.getScreenSize().getWidth();
		 int Height = (int)toolkit.getScreenSize().getHeight();
		 getDriver().manage().window().setSize(new Dimension(Width,Height));
   }
   
   public static synchronized void setBrowserDimensions(int width, int height) throws Exception {
		 getDriver().manage().window().setSize(new Dimension(width, height));
   }
   
   

}
