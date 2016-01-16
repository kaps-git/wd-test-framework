package com.korg.frontend.test;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.testng.annotations.Test;

import com.korg.common.util.FileUtil;

public class BaseTest {

	@Test
	public void readBrowsersForExecution() throws Exception {
		String filepath = new java.io.File(".").getCanonicalPath() + "/src/test/resources/configBrowsers.json";
		System.out.println("File path : " + filepath);
		FileUtil fUtil = new FileUtil();
		String json = fUtil.readFileContentsAsString(filepath);
		
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

	}
}
