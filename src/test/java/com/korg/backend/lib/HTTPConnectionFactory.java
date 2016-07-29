package com.korg.backend.lib;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;

import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;

/**
 * 
 * @author akapil
 *
 */
public class HTTPConnectionFactory implements HttpURLConnectionFactory {
	String proxyHost;
    Integer proxyPort;
    
	Proxy proxy;
	Type proxyType;

    public String getProxyHost() {
		return proxyHost;
	}
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	public Integer getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

    public Proxy getProxy() {
		return proxy;
	}
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
	public Type getProxyType() {
		return proxyType;
	}
	public void setProxyType(Type proxyType) {
		this.proxyType = proxyType;
	}
	
	private void initProxy() {
        proxy = new Proxy(proxyType, new InetSocketAddress(proxyHost, proxyPort));
    }
	
	public HTTPConnectionFactory(Type proxyType , String proxyHost, Integer proxyPort) {
		this.proxyType = proxyType;
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
	}
    
	public HTTPConnectionFactory(String proxyType , String proxyHost, Integer proxyPort) {
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		
		switch(proxyType.toLowerCase()) {
			case "http" :
				this.proxyType = Proxy.Type.HTTP;
				break;
				
			case "socks" :
				this.proxyType = Proxy.Type.SOCKS;
				break;
			
			default:
				this.proxyType = Proxy.Type.DIRECT;
				break;
		}
		
	}
	
	@Override
	public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        initProxy();
        return (HttpURLConnection) url.openConnection(proxy);
    }

}//end class
