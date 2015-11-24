package com.korg.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.csvreader.CsvReader;

public class CsvUtil {
	
	private static final Logger logger=Logger.getLogger(CsvUtil.class.getName());
	
	public static void sortCsvFile(String fileName,String field){
        if(StringUtils.isBlank(field)){
                return;
        }
        File inputFile=new File(fileName);
        File sortedFile=new File(fileName.concat("_sorted"));
        logger.info("Sorting : " + inputFile.getName() + " on field : " + field);

        Map<String, List<String>> map = new TreeMap<String, List<String>>();

        try {
            CsvReader reader = new CsvReader(new FileReader(fileName));
            reader.setTrimWhitespace(false);
            reader.readHeaders();
            String headers=reader.getRawRecord();
            while(reader.readRecord()) {
                String key=reader.get(field);
                String line=reader.getRawRecord();
                //logger.info("LINE : " + line);
                List<String> l = map.get(key);
                if (l == null) {
                    l = new LinkedList<String>();
                    map.put(key, l);
                }
                l.add(line);
            }
            reader.close();

            FileWriter writer = new FileWriter(sortedFile);
            writer.write(headers+"\n");
            for (List<String> list : map.values()) {
                for (String val : list) {
                    writer.write(val);
                    writer.write("\n");
                }
            }
            writer.close();
            FileUtils.copyFile(sortedFile, inputFile);
            FileUtils.deleteQuietly(sortedFile);
        } catch (FileNotFoundException e) {
            logger.error("Unable to open csv file : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Error reading csv file content : " + e.getMessage() );
            e.printStackTrace();
        }

    }//end method
	
	
	public static Boolean generateHtmlFileFromCsvFile(String csvFileFullPath,String headingString){
        logger.info("Generating HTML report from CsvFile : " + csvFileFullPath);
        Boolean isSuccess=true;
        CsvReader reader;
        ArrayList<Map<String,String>> records = new ArrayList<Map<String,String>>();
        String[] headers=null;
        Map<String, String> map = null;
        String htmlReportFile=csvFileFullPath.replace("csv","html");
        try {
            reader = new CsvReader(new FileReader(csvFileFullPath));
            reader.setTrimWhitespace(false);

            reader.readHeaders();
            headers=reader.getHeaders();
            while(reader.readRecord()) {
                map = new HashMap<String, String>();
                for(String header : headers){
                    String value=reader.get(header);
                    /*Make value link if it contains http://*/
                    if(value.contains("http://")){
                        value="<A HREF='"+ value +"'>" + value +"</A>";
                    }
                    logger.debug("Header : " + header + " Value : " + value);
                    map.put(header,value);
                }
                records.add(map);
            }
            reader.close();

        } catch(Exception e){
            logger.error("Error opening file : " + csvFileFullPath + " " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        try {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            VelocityContext context = new VelocityContext();
            context.put("heading",headingString);
            context.put("headers",headers);
            context.put("records", records);

            Template t = ve.getTemplate( "SbsPlatform/be-data/generic-csv2html-template.vm" );

            StringWriter writer = new StringWriter();
            t.merge( context, writer );
            File file = new File(htmlReportFile);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(writer.toString());
            bw.close();
            logger.info("Generated HTML report file : " + htmlReportFile);
        } catch (Exception e) {
            logger.error("Error generating html : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return isSuccess;
    }//end method
	
	
	public static Boolean generateHtmlFileFromCsvFile(String csvFileFullPath,String headingString,String csvSortField){
        logger.info("Generating HTML report from CsvFile : " + csvFileFullPath);
        
        sortCsvFile(csvFileFullPath,csvSortField);
        
        Boolean isSuccess=true;
        CsvReader reader;
        ArrayList<Map<String,String>> records = new ArrayList<Map<String,String>>();
        String[] headers=null;
        Map<String, String> map = null;
        String htmlReportFile=csvFileFullPath.replace("csv","html");
        try {
            reader = new CsvReader(new FileReader(csvFileFullPath));
            reader.setTrimWhitespace(false);

            reader.readHeaders();
            headers=reader.getHeaders();
            while(reader.readRecord()) {
                map = new HashMap<String, String>();
                for(String header : headers){
                    String value=reader.get(header);
                    /*Make value link if it contains http://*/
                    if(value.contains("http://")){
                        value="<A HREF='"+ value +"'>" + value +"</A>";
                    }
                    logger.debug("Header : " + header + " Value : " + value);
                    map.put(header,value);
                }
                records.add(map);
            }
            reader.close();

        }catch(Exception e){
            logger.error("Error opening file : " + csvFileFullPath + " " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        try {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            VelocityContext context = new VelocityContext();
            context.put("heading",headingString);
            context.put("headers",headers);
            context.put("records", records);

            Template t = ve.getTemplate( "SbsPlatform/be-data/generic-csv2html-template.vm" );

            StringWriter writer = new StringWriter();
            t.merge( context, writer );
            File file = new File(htmlReportFile);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(writer.toString());
            bw.close();
            logger.info("Generated HTML report file : " + htmlReportFile);
        } catch (Exception e) {
            logger.error("Error generating html : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return isSuccess;
    }//end method

}
