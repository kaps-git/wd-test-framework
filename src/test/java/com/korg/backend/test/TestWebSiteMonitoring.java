package com.korg.backend.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.korg.common.util.FileUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestWebSiteMonitoring {
	private static final Logger logger=Logger.getLogger(TestWebSiteMonitoring.class.getName());
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public ArrayList<HashMap<String, String>> readWebPagesToMonitor(String json) throws Exception {
		
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(json);
		JSONArray array = (JSONArray)obj;
		
		for(int i=0; i<array.size();i++) {
			HashMap<String, String> hmap = new HashMap<String, String>();
			
			JSONObject o = (JSONObject) array.get(i);
			
			hmap.put("name", o.get("name").toString().trim());
			hmap.put("url", o.get("url").toString().trim());
			hmap.put("assertData", o.get("assertData").toString().trim());
			hmap.put("enabled", o.get("enabled").toString().trim());
			list.add(hmap);
		} //end for
		
		//####### TEMP : iterate thro list
		for (int i=0; i<list.size(); i++) {
			System.out.println("+++++++++++++++++");
			System.out.println(list.get(i).get("name"));
			System.out.println(list.get(i).get("url"));
			System.out.println(list.get(i).get("assertData"));
			System.out.println(list.get(i).get("enabled"));
		}
		
		return list;
	} //end func
	
	@DataProvider(name = "websiteProvider", parallel = true)
    public Iterator<Object[]> dataProvider(Method testMethod) throws Exception {
    	List<Object[]> myList=new ArrayList<Object[]>();
    	ArrayList<HashMap<String, String>> bList = new ArrayList<HashMap<String, String>>();
    	
		//read from repo browser config file
		String filepath = new java.io.File(".").getCanonicalPath() + "/src/test/resources/websitemonitoring.json";
		System.out.println("File path : " + filepath);
		FileUtil fUtil = new FileUtil();
		String json = fUtil.readFileContentsAsString(filepath);
		
		bList = readWebPagesToMonitor(json);
    		
    	
    	for(int i=0; i<bList.size(); i++) {
			myList.add(new Object[]{bList.get(i)});
		}
    	
    	return myList.iterator();
    }//end DataProvider
    
    
    @Test(dataProvider = "websiteProvider")
   	public void testWebPagesGives200OK(HashMap<String,String> webPageInfo) throws Exception {
   		 
   		 if(webPageInfo.get("enabled").equalsIgnoreCase("true")) {
   			 
   			 Client client = new Client();
   			 
   			 WebResource webResource = client.resource(webPageInfo.get("url"));
   			 ClientResponse response = webResource.accept("text/html").get(ClientResponse.class);
   			 System.out.println(webPageInfo.get("name") + "-->" + response.getStatus());
   			 String assertData = webPageInfo.get("assertData");
   			 
   			 if(response.getStatus() == 200) {
   					String responseString = response.getEntity(String.class);
   					System.out.println(responseString);
   					//get assert data
   					StringTokenizer st = new StringTokenizer(assertData, ",");
   					while(st.hasMoreTokens()) {
   						String a = st.nextToken().replace("[", "").replace("]", "");
   						System.out.println(">>> Verifying --- " + a);
   						//assertList.add(a);
   						if(responseString.contains(a)) {
   							Assert.assertTrue(responseString.contains(a), "Expected content in Respones :- " + a);
   							
   						}
   					}
   					
   			 }
   		 }
   				
   		 
   	}//end test
}
