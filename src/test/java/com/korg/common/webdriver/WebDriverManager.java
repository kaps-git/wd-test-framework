package com.korg.common.webdriver;

import org.openqa.selenium.WebDriver;

public class WebDriverManager {
	
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<String> sessionId = new ThreadLocal<String>();	//For saucelabs

	 
    public static WebDriver getDriver() {
        return webDriver.get();
    }
 
    static void setWebDriver(WebDriver driver) {
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


}
