package com.korg.common.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserProxyHandler {
	public static Logger logger = Logger.getLogger(BrowserProxyHandler.class.getName());
	
	private DesiredCapabilities caps;
	
	public BrowserProxyHandler(DesiredCapabilities capabilities) {
		caps = capabilities;
		logger.info("Initialized " + BrowserProxyHandler.class.getName());
	}
	
	public DesiredCapabilities setProxyAutoConfigUrl(String proxyPACUrl) {
		
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		
		if(!proxyPACUrl.equals("")) {
			logger.info("Setting Proxy PAC -> " + proxyPACUrl);
			proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.PAC);
			proxy.setProxyAutoconfigUrl(proxyPACUrl);
			caps.setCapability(CapabilityType.PROXY, proxy);
			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			caps.setAcceptInsecureCerts(true);
		} else {
			logger.info("Setting DIRECT connection avoiding Proxy");
			proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.DIRECT);
			caps.setCapability(CapabilityType.PROXY, proxy);
		}
		
		return caps;
	}//method
	
	public DesiredCapabilities setHttpProxy(String host, String port) {
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		String httpProxy = String.format("%s:%s", host.trim(), port.trim());
		logger.info("Setting HTTP Proxy to ->" + httpProxy);
		proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.MANUAL);
		proxy.setAutodetect(false);
		proxy.setHttpProxy(httpProxy);
		caps.setCapability(CapabilityType.PROXY, proxy);
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		caps.setAcceptInsecureCerts(true);
		return caps;
	}//method
	
	public DesiredCapabilities setSocksProxy(String host, String port) {
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		String socksProxy = String.format("%s:%s", host.trim(), port.trim());
		logger.info("Setting SOCKS Proxy to ->" + socksProxy);
		proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.MANUAL);
		proxy.setAutodetect(false);
		proxy.setSocksProxy(socksProxy);
		caps.setCapability(CapabilityType.PROXY, proxy);
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		caps.setAcceptInsecureCerts(true);
		return caps;
	}//method
	
	public DesiredCapabilities setFTPProxy(String host, String port) {
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		String ftpProxy = String.format("%s:%s", host.trim(), port.trim());
		logger.info("Setting FTP Proxy to ->" + ftpProxy);
		proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.MANUAL);
		proxy.setAutodetect(false);
		proxy.setFtpProxy(ftpProxy);
		caps.setCapability(CapabilityType.PROXY, proxy);
		return caps;
	}
	
	
}//class
