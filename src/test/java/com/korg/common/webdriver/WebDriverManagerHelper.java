package com.korg.common.webdriver;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;


/**
 * YSB's WebdriverManager to init webdriver for differnet usecases and browsers
 * @author akapil
 *
 */
public class WebDriverManagerHelper  {
	
	
	public static String BROWSER = "BROWSER";
	public static String VERSION = "VERSION";
	public static String OS = "OS";
	public static String PROXY_TYPE = "PROXY_TYPE";
	public static String PROXY_PAC_URL = "PROXY_PAC_URL";
	public static String CHROME_DRIVER_PATH = "CHROME_DRIVER_PATH";
	public static String CHROME_BROWSER_PATH = "CHROME_BROWSER_PATH";
	public static String FIREFOX_PROFILE_DIR = "FIREFOX_PROFILE_DIR";
	public static String FIREFOX_PROFILE_NAME = "FIREFOX_PROFILE_NAME";
	public static String CHROME_PROFILE = "CHROME_PROFILE";
	public static String HUB_HOST = "HUB_HOST";
	public static String HUB_PORT = "HUB_HOST";
	
	private boolean runOnSauceLabs = false;
	
	
	private enum TYPE {
		IE, FF, CHROME, SAFARI, IPHONE
	}
	
	private TYPE browserType;
	
	private SauceOnDemandAuthentication authentication;
	
	public  void setSauceAuthentication(String sauceUserName, String sauceKey) {
		authentication = new SauceOnDemandAuthentication(sauceUserName, sauceKey);
	}
   
	public void useSauceLabs(boolean flag) {
		runOnSauceLabs = flag;
	}
	/**
	 * 
	 * @param browser 
	 * @author akapil
	 */
	private void setBrowserType (String browser)  {
		
		if(browser.equalsIgnoreCase("IE") || 
		   browser.equalsIgnoreCase("iexplore") ||
		   browser.equalsIgnoreCase("internet explorer")) {
			browserType = TYPE.IE;
    	} else if(browser.equalsIgnoreCase("FF") || browser.equalsIgnoreCase("firefox")){
    		browserType = TYPE.FF;
    	} else if(browser.equalsIgnoreCase("chrome")){
    		browserType = TYPE.CHROME;
    	} else if(browser.equalsIgnoreCase("safari")){
    		browserType = TYPE.SAFARI;
    	} else if(browser.equalsIgnoreCase("iphone")){
    		browserType = TYPE.IPHONE;
    	} else {
    		System.err.println("Failed to understand browser:" + browser);
    	}
	}
	
	
    /**
     *
     * @param browser Represents the browser to be used as part of the test run.
     * @param version Represents the version of the browser to be used as part of the test run.
     * @param os Represents the operating system to be used as part of the test run.
     * @return
     * @throws MalformedURLException if an error occurs parsing the url
     * @author akapil
     */
    public void initSauceDriver(String browser, String version, String os) throws MalformedURLException {
    	 //check if to execute on Sauce Lab
        if(runOnSauceLabs) {
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
	        if (version != null) {
	            capabilities.setCapability(CapabilityType.VERSION, version);
	        }
	        capabilities.setCapability(CapabilityType.PLATFORM, os);
	        capabilities.setCapability("name", "Sauce Sample Test");
	        
	        WebDriverManager.setWebDriver(new RemoteWebDriver(
	                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
	                capabilities));
	        WebDriverManager.setSessionID(((RemoteWebDriver) WebDriverManager.getDriver()).getSessionId().toString());
        }
        
    }//end func

    /**
     * 
     * @param configMap  which is key/value pair of all params needed for driver init
     * @throws Exception
     * @author akapil
     */
    public void initDriver(HashMap<String, String> configMap) throws Exception {
    	
    	DesiredCapabilities capabilities = new DesiredCapabilities();
    	String hubHost=null, hubPort=null, browser = null;
    	
    	for (String key : configMap.keySet()) {
    		
    		//set browser name
    		if(key.equals(BROWSER)){
    			browser = configMap.get(key);
    			setBrowserType(browser);
    			capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
    		}
    		
    		//set browser version
    		if(key.equals(VERSION)){
    			capabilities.setCapability(CapabilityType.VERSION, configMap.get(key));
    		}
    		
    		//set platform
    		if(key.equals(OS)){
    			capabilities.setCapability(CapabilityType.PLATFORM, configMap.get(key));
    		}
    		
    		//proxy type and Proxy pac URL
    		if(key.equals(PROXY_PAC_URL)) {
    			String proxyPACUrl = configMap.get(key).trim();
    			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
    			
    			if(!proxyPACUrl.equals("")) {
    				proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.PAC);
    				proxy.setProxyAutoconfigUrl(proxyPACUrl);
    			} else {
    				proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.DIRECT);
    				
    			}
    			
    			capabilities.setCapability(CapabilityType.PROXY, proxy);
    		}
    		
    		if(key.equals(FIREFOX_PROFILE_DIR) ) {
    			FirefoxProfile firefoxProfile;
				firefoxProfile = new FirefoxProfile(new File(configMap.get(key)) );
				firefoxProfile.setAcceptUntrustedCertificates(true);
				firefoxProfile.setPreference("security.use_mozillapkix_verification", false);
				capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
    			
    		}
    		
    		if(key.equals(FIREFOX_PROFILE_NAME)) {
    			FirefoxProfile firefoxProfile;
    			ProfilesIni allProfiles = new ProfilesIni();
				firefoxProfile = allProfiles.getProfile(configMap.get(key));
				firefoxProfile.setAcceptUntrustedCertificates(true);
				firefoxProfile.setPreference("security.use_mozillapkix_verification", false);
				capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
    		}
    		
    		if(key.equals(CHROME_PROFILE)) {
    			capabilities.setCapability("chrome.switches", Arrays.asList("--user-data-dir=" + configMap.get(key)));
    		}
    		
    		if(key.equals(HUB_HOST)) {
    			hubHost = configMap.get(key);
    			if(configMap.containsKey(HUB_PORT)) {
    				hubPort = configMap.get(HUB_PORT);
    			} else {
    				hubPort = "4444";
    			}
    		}
    		
    	}//end for
    	
        
    	String wdHuburl = "";
    	
        //check if to execute on Sauce Lab
        if(runOnSauceLabs) {
        	//capabilities.setCapability("name", "Sauce Sample Test");
        	wdHuburl = "http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + 
        			"@ondemand.saucelabs.com:80/wd/hub";
        	
        	System.out.println("INFO : Sauce Lab Webdriver Hub URL ==> " + wdHuburl);
        	WebDriverManager.setWebDriver(new RemoteWebDriver(new URL(wdHuburl), capabilities));
        	WebDriverManager.setSessionID( ((RemoteWebDriver) WebDriverManager.getDriver()).getSessionId().toString());
	        
        } else if(hubHost != null) {
        	wdHuburl = "http://" + hubHost + ":" + hubPort + "/wd/hub";
        	System.out.println("INFO : Remote Web Driver url ==> " + wdHuburl);
        	WebDriverManager.setWebDriver(new RemoteWebDriver(new URL(wdHuburl), capabilities));
        	
        } else {
        	switch(browserType) {
	        	case FF:
	        		WebDriverManager.setWebDriver(new FirefoxDriver(capabilities));
					break;
					
				case IE:
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					WebDriverManager.setWebDriver(new InternetExplorerDriver(capabilities));
					break;
					
				case CHROME:
					if(configMap.containsKey(CHROME_DRIVER_PATH)) {
						
						System.setProperty("webdriver.chrome.driver", configMap.get(CHROME_DRIVER_PATH));
						
						if(configMap.containsKey(CHROME_BROWSER_PATH)) {
							capabilities.setCapability("chrome.binary", configMap.get(CHROME_BROWSER_PATH));
						} else {
							throw new Exception("Chrome browser location path not specified. Can not start webdriver.");
						}
						capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
						WebDriverManager.setWebDriver(new ChromeDriver(capabilities));
					} else {
						throw new Exception("Chrome driver path not specified. Can not start webdriver.");
					}
					break;
					
				case SAFARI:
					throw new Exception("TODO : Can not init webdriver for Safari on local machine.");
					
					
				case IPHONE:
					throw new Exception("TODO : Can not init webdriver for iphone on local machine.");
					
				default:
					throw new Exception("TODO : Unsupported driver.");
        	}//end swtich
        }//end else
        
        
    }//end func


}//end class
