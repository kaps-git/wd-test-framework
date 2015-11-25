package com.korg.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
	private String filePath;
	private String charset = "UTF-8";
	private boolean isAppend = false;
	private boolean autoFlush = false;
	
	public FileUtil() {
		
	}
	
	public void setFilePath(String value) {
		filePath = value;
	}
	
	public void setFileEncoding(String value) {
		charset = value;
	}
	
	public void appendToFile(boolean value) {
		isAppend = value;
	}
	
	public void autoFlush(boolean value) {
		autoFlush = value;
	}
	
    public String readFile(String path, Charset encoding) throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
	public String readFileContentsAsString(String absoluteFilePath) throws Exception {
		//defualting to UTF8 encoding
		String resString = IOUtils.toString(new FileInputStream(new File(absoluteFilePath)), "UTF-8");
        return resString;
	}//end method
	
	public String readFileContentsAsString(InputStream inputStream){
        String fileContent=null;
        try {
            fileContent=IOUtils.toString(inputStream);
        } catch (IOException e){
            logger.error("Unable to read file contents into string : " + e.getMessage());
            fileContent=null;
        }

        return fileContent;
    }
	
	public void writeLine(String content) throws Exception {
		File file = new File(filePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		FileOutputStream fos = new FileOutputStream(file, isAppend);
		OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
		BufferedWriter bw = new BufferedWriter(osw);
		PrintWriter pw = new PrintWriter(bw, autoFlush);
		pw.write(content);
		pw.close();
		
	}
	
}
