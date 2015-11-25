package com.korg.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minidev.json.JSONArray;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;

public class JsonUtil {
	
	private static final Logger logger=Logger.getLogger(JsonUtil.class.getName());
	
	public JsonUtil(){
		logger.info("~~~~ Constructor ~~~~");
	}
	
	public Boolean checkIfElementExistsInJson(String json,String jsonPath){
        logger.info("Check existence with JsonPath : " + jsonPath);
        Boolean exists=false;
        try {
            Configuration conf = Configuration.builder().options(Option.ALWAYS_RETURN_LIST).build();
            //conf.addOptions(Option.ALWAYS_RETURN_LIST);
            List<Object> valueList= JsonPath.using(conf).parse(json).read(jsonPath);
            int count=valueList.size();
            logger.info("Count is : " + count);
            if(count>0){
                exists=true;
            }
        }catch (PathNotFoundException pathNotFoundException) {
            exists=false;
            logger.error("Error : " + pathNotFoundException.getMessage());
        }
        return exists;
    }//end method
	
	public int getFieldCountFromJson(String json,String jsonPath){
        logger.info("Getting count for jsonPath : " + jsonPath);
        int count=0;
        try {
            Configuration conf = Configuration.defaultConfiguration();
            conf.addOptions(Option.ALWAYS_RETURN_LIST);
            List<Object> valueList= JsonPath.using(conf).parse(json).read(jsonPath);
            count=valueList.size();
            logger.info("Count is : " + count);
        } catch (PathNotFoundException pathNotFoundException) {
            count=0;
            logger.error("Error : " + pathNotFoundException.getMessage());
        }
        return count;
    }
	
	public String beautifyJsonString(String json){
        String beautifiedJson=json;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json);
            beautifiedJson=gson.toJson(je);
        } catch (Exception e){
            logger.error("Unable to beautify json : " + e.getMessage());
        }
        return  beautifiedJson;
    }
	
	public String beautifyJsonString(String json,boolean disableEscape){
        String beautifiedJson=json;
        try {
        Gson gson = null;
        if(disableEscape){
            gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        } else {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);

        beautifiedJson=gson.toJson(je);
        }catch (Exception e){
            logger.error("Unable to beautify json : " + e.getMessage());
        }

        return   beautifiedJson;
    }
	
	public List readValueFromJson(String json,String jsonPath){
        List<Object> valueList=null;   //Made as string list, since 1 is returned as 1.0 by Gson.
        Object value=null;
        logger.info("Getting value for jsonPath : " + jsonPath);
        try {
            value=JsonPath.parse(json).read(jsonPath);

            if(JsonPath.isPathDefinite(jsonPath)){
                valueList=new ArrayList<Object>();
                valueList.add(value);
                return  valueList;
            }

            if(value instanceof JSONArray){
                JSONArray jsonArrayObjectValue=(JSONArray)value;
                valueList =new ArrayList<Object>();
                for(int i=0;i<jsonArrayObjectValue.size();i++){
                    valueList.add(jsonArrayObjectValue.get(i));
                }
            } else{
                Configuration conf = Configuration.defaultConfiguration();
                conf.addOptions(Option.ALWAYS_RETURN_LIST);
                valueList= JsonPath.using(conf).parse(json).read(jsonPath);
            }

            int count=valueList.size();
            if(count==0){
                valueList=null;
                return valueList;
            }
        }catch (PathNotFoundException pathNotFoundException) {
            logger.error("Error : " + pathNotFoundException.getMessage());
            pathNotFoundException.printStackTrace();
            valueList=null;
        }
        /* Added since 1 is returned as 1.0 */
        if(valueList != null)   {
            List<Object> newValueList= new ArrayList<Object>();
            Iterator it=valueList.iterator();
            while(it.hasNext()){
                Object obj= it.next();
                if(DataUtil.isInteger(obj)){
                    Double d = new Double((Double)obj);
                    newValueList.add(d.intValue());
                } else {
                    newValueList.add(obj);
                }
            }
            return  newValueList;
        }

        return valueList;
    }//end method

}
