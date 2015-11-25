package com.korg.common.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.*;

/**
 * 
 * @author Kapil Athalye
 *
 */
public class ExcelUtil {
	
	private static final Logger logger=Logger.getLogger(ExcelUtil.class.getName());
	
	public ExcelUtil(){
		logger.info("~~~~ Constructor ~~~~");
	}
	
	/**
	 * 
	 * @param cell
	 * @return
	 * @author Kapil Athalye
	 */
	public String getStringCellValue(HSSFCell cell){
		String returnVal = "";
		
		if(cell != null){
			
			int cellType = cell.getCellType();
			
			try{
				if(cellType == cell.CELL_TYPE_BLANK) { 
						returnVal = "";
				} else if (cellType == cell.CELL_TYPE_BOOLEAN){
					
					returnVal = String.valueOf(cell.getBooleanCellValue());
					
				} else if(cellType == cell.CELL_TYPE_NUMERIC){
					
					returnVal = String.valueOf(cell.getNumericCellValue()).replaceAll(".0", "").trim();
				} else {
					returnVal = cell.getStringCellValue();
				}
			} catch (Exception e){
				System.out.println("Failed to get Cell Value. Cell Type = " + cell.getCellType());
				returnVal = "";
			}
		}
		
		return returnVal;
		
	}//end func
	
	/**
	 * 
	 * @param inputDataXlsFile
	 * @param arrList
	 * @return
	 * @author Kapil Athalye
	 */
	public  ArrayList<HashMap<String,String>> readExcel(String inputDataXlsFile, ArrayList<String> arrList) {
		
		ArrayList<HashMap<String,String>> rowList = new ArrayList<HashMap<String,String>>(); 
		
		try {
			File xlsFile = new File(inputDataXlsFile);
			
			POIFSFileSystem fs = new POIFSFileSystem( new FileInputStream(xlsFile));
			
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);			
			HSSFRow row;
			HSSFCell cell;			
			
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			int cols = 0; // No of columns
			int tmp = 0;

			
			for (int j = 0; j < 10 || j < rows; j++) {
				row = sheet.getRow(j);
				if (row != null) {
					tmp = sheet.getRow(j).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
					
				}
			}
			
			System.out.println("number of colums ="+cols);

			for(int r = 1; r <= rows; r++) {			
				row = sheet.getRow(r);
				HashMap<String, String> record = new HashMap<String, String>();
				if (row != null) {
					
					for (int c = 1; c <= cols && c <= arrList.size(); c++ ) {
						cell = row.getCell(c);	
						System.out.println(getStringCellValue(cell));
						record.put(arrList.get(c-1).toString(), getStringCellValue(cell));						
					}//end for
						
				}//end if
				
				rowList.add(record);
				
			}//end for			
			
			
			
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return rowList;
	}//end func

	/**
	 * 	
	 * @param inputDataXlsFile
	 * @param sheetName
	 * @param arrList
	 * @return
	 * @author Kapil Athalye
	 */
	 public ArrayList<HashMap<String,String>> readExcel(String inputDataXlsFile, String sheetName, ArrayList<String> arrList) {
		
		ArrayList<HashMap<String,String>> rowList = new ArrayList<HashMap<String,String>>(); 
		
		try {
			File xlsFile = new File(inputDataXlsFile);
			
			POIFSFileSystem fs = new POIFSFileSystem( new FileInputStream(xlsFile));
			
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet(sheetName);			
			HSSFRow row;
			HSSFCell cell;			
			
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			int cols = 0; // No of columns
			int tmp = 0;

			
			for (int j = 0; j < 10 || j < rows; j++) {
				row = sheet.getRow(j);
				if (row != null) {
					tmp = sheet.getRow(j).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
					
				}
			}
			
			System.out.println("number of colums ="+cols);

			for(int r = 1; r <= rows; r++) {			
				row = sheet.getRow(r);
				HashMap<String, String> record = new HashMap<String, String>();
				if (row != null) {
					
					for (int c = 1; c <= cols && c <= arrList.size(); c++ ) {
						cell = row.getCell(c);	
						System.out.println(getStringCellValue(cell));
						record.put(arrList.get(c-1).toString(), getStringCellValue(cell));						
					}//end for
						
				}//end if
				
				rowList.add(record);
				
			}//end for			
			
			
			
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return rowList;
	}//end func
	
}
