package com.korg.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minidev.json.JSONArray;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;

public class FileUtil {
	
	private static final Logger logger=Logger.getLogger(FileUtil.class.getName());
	
    public static String readFile(String path, Charset encoding) throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
	public static String readFileContentsAsString(String absoluteFilePath) throws Exception {
		//defualting to UTF8 encoding
		String resString = IOUtils.toString(new FileInputStream(new File(absoluteFilePath)), "UTF-8");
        return resString;
	}//end method
	
	public  static String readFileContentsAsString(InputStream inputStream){
        String fileContent=null;
        try {
            fileContent=IOUtils.toString(inputStream);
        } catch (IOException e){
            logger.error("Unable to read file contents into string : " + e.getMessage());
            fileContent=null;
        }

        return fileContent;
    }
	
}
