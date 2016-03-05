package com.korg.frontend.test;


import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.korg.common.util.FileUtil;
import com.korg.common.webdriver.WebDriverManager;
import com.korg.entity.PaymentMethod;
import com.korg.entity.TestUser;
import com.korg.entity.deserializer.PaymentMethodsDeserializer;
import com.korg.entity.deserializer.UserDeserializer;

public class BaseTest {

	private static final Logger logger=Logger.getLogger(BaseTest.class.getName());
	
	public ArrayList<HashMap<String, String>> readBrowsersForExecution(String json) throws Exception {
		
		
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(json);
		JSONArray array = (JSONArray)obj;
		
		for(int i=0; i<array.size();i++) {
			HashMap<String, String> hmap = new HashMap<String, String>();
			
			JSONObject o = (JSONObject) array.get(i);
			
			hmap.put("browser", o.get("browser").toString().trim());
			hmap.put("version", o.get("version").toString().trim());
			hmap.put("os", o.get("os").toString().trim());
			hmap.put("selected", o.get("selected").toString().trim());
			list.add(hmap);
		} //end for
		
		//####### TEMP : iterate thro list
		for (int i=0; i<list.size(); i++) {
			System.out.println("+++++++++++++++++");
			System.out.println(list.get(i).get("browser"));
			System.out.println(list.get(i).get("version"));
			System.out.println(list.get(i).get("os"));
			System.out.println(list.get(i).get("selected"));
		}
		
		return list;
	} //end func
	
	@DataProvider(name = "defaultBrowserProvider", parallel = true)
    public Iterator<Object[]> defaultBrowserProvider(Method testMethod) {
    	 
    	List<Object[]> myList=new ArrayList<Object[]>();
    	
    	HashMap<String,String> b1 = new HashMap<String,String>();
    	 b1.put("browser", "chrome");
    	 b1.put("version", "47");
    	 b1.put("os", "OS X 10.9");
    	 
    	 HashMap<String,String> b2 = new HashMap<String,String>();
    	 b2.put("browser", "firefox");
    	 b2.put("version", "43.0");
    	 b2.put("os", "OS X 10.9");
    	 
    	 myList.add(new Object[]{b1});
    	 myList.add(new Object[]{b2});
    	 
    	 
    	 return myList.iterator();
    }//end DataProvider
    
    
    @DataProvider(name = "browserProvider", parallel = true)
    public Iterator<Object[]> browserProvider(Method testMethod) throws Exception {
    	List<Object[]> myList=new ArrayList<Object[]>();
    	ArrayList<HashMap<String, String>> bList = new ArrayList<HashMap<String, String>>();
    	
    	String envBrowserConfJson = System.getenv("BROWSER_CONFIG");
		logger.info("Environment BROWSER_CONFIG : " + envBrowserConfJson);
		
    	if(envBrowserConfJson == null) {
    		//read from repo browser config file
    		String filepath = new java.io.File(".").getCanonicalPath() + "/src/test/resources/configBrowsers.json";
    		System.out.println("File path : " + filepath);
    		FileUtil fUtil = new FileUtil();
    		String json = fUtil.readFileContentsAsString(filepath);
    		
    		bList = readBrowsersForExecution(json);
    		
    	} else {
    		bList = readBrowsersForExecution(envBrowserConfJson);
    	}
    	
    	for(int i=0; i<bList.size(); i++) {
			myList.add(new Object[]{bList.get(i)});
		}
    	
    	return myList.iterator();
    }//end DataProvider
    
    
    
    @Test(dataProvider = "browserProvider")
	public void TestDisplayBrowserConfig(HashMap<String,String> browserInfo) throws Exception {
		String browser = browserInfo.get("browser");
		String version = browserInfo.get("version");
		String os = browserInfo.get("os");
		
		System.out.println("Browser = " + browser);
		System.out.println("version = " + version);
		System.out.println("os = " + os);
		
    }
    
    
    @Test
	public void TestReadTestUserFromJson() throws Exception {
		String filepath = new java.io.File(".").getCanonicalPath() + "/src/test/resources/testUsers.json";
		
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(TestUser.class, new UserDeserializer());
		gsonBuilder.registerTypeAdapter(PaymentMethod.class, new PaymentMethodsDeserializer());
		
		
		JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(filepath)));
		JsonParser jsonParser = new JsonParser();
		JsonElement e = jsonParser.parse(reader);
		
		if(e.isJsonObject()){
			JsonObject o = e.getAsJsonObject();
			JsonElement userE = o.get("qa").getAsJsonObject().get("general");
			Gson gson = gsonBuilder.create();
			TestUser u = gson.fromJson(userE, TestUser.class);
			System.out.println(u.toString());
			
			for(PaymentMethod p : u.getPaymentMethodList()) {
				System.out.println(p.getCcType() + "|" + p.getCcNumber() + "|" + p.getCvv() + "|" + p.getExpiryMonth() + "|" + p.getExpiryYear()) ;
			}
			
		} else {
			System.out.println("Not Json Element .....");
		}
		
	}
}
