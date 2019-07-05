package com.nitor.dux.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines methods for retrieving content from excel in various formats. At present the support is for Excel 2007 and above.
 * @author anup.manekar
 *
 */
public class ExcelOperations {
	
	private static final String FILE_NOTATION = "file:";
	private static String sDataPath = "data/" + "/" + System.getProperty("environment") + "/";
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelOperations.class);
	
	/**
	 * Return excel content in form of row-column matrix
	 * @param fileName - Excel filename to be read
	 * @param sheetName - Sheet within excel to be read. This is not sheet index but sheet name.
	 * @param filterCondition - Any filter condition (ColName:Value) to be used while retrieving data.
	 * @return
	 */
	public static List<HashMap<String,Object>> readExcel(String fileName,String sheetName,
			HashMap<String,String> filterCondition){
		List<HashMap<String,Object>> records = new ArrayList<HashMap<String,Object>>();
		List<String> headers = new ArrayList<String>();
		
		try{
			String excelFilePath = ExcelOperations.class.getClassLoader().getResource(fileName).getFile();
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	        XSSFSheet firstSheet = workbook.getSheet(sheetName);
	        Iterator<Row> iterator = firstSheet.iterator();
	        if(iterator.hasNext()){
	        	Row nextRow = iterator.next();
	        	Iterator<Cell> cellIterator = nextRow.cellIterator(); 
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                headers.add(cell.getStringCellValue());
	            }
	        }
	        
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            HashMap<String,Object> rowRecord = new HashMap<String,Object>();
	            Iterator<Cell> cellIterator = nextRow.cellIterator(); 
	            int colNum = 0;
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                Object value = getCellValueIrrespectiveOfType(cell);
	                if(getCellValueIrrespectiveOfType(cell).toString().contains(FILE_NOTATION)){
	                	String embeddedFileName = getCellValueIrrespectiveOfType(cell).toString().substring(FILE_NOTATION.length());
	                	String embeddedFilePath = sDataPath.concat(embeddedFileName);
	                	JSONObject jsonContents = FileOperations.getFileContentsInJson(embeddedFilePath);
	                	value = jsonContents;
	                }	                
	                rowRecord.put(headers.get(colNum), value);
	                colNum++;
	            }
	            if(filterCondition != null){
	            	Map.Entry<String, String> entry=filterCondition.entrySet().iterator().next();
	            	if(rowRecord.get(entry.getKey()).toString().equals(entry.getValue()))
	            		records.add(rowRecord);
                }
                else{
                	records.add(rowRecord);
                }
	           
	        }
	         
	        workbook.close();
	        inputStream.close();
	        return records;
		}catch(FileNotFoundException e){
			LOGGER.error("File not found:" + e.getMessage());
			throw new Error(e);	
		}catch(IOException e) {
			LOGGER.error("Error reading file:" + e.getMessage());
			throw new Error(e);
		}
		
	}
	
	/**
	 * Return excel content in form of col-row matrix. 
	 * @param fileName
	 * @param sheetName
	 * @return
	 */
	public static HashMap<String,ArrayList<Object>> getColumnDataAsArrays(String fileName,String sheetName){
		HashMap<String,ArrayList<Object>> result = new HashMap<String,ArrayList<Object>>();
		List<String> headers = new ArrayList<String>();
		ArrayList<Object> curValue = new ArrayList<Object>();
		String excelFilePath = ExcelOperations.class.getClassLoader().getResource(fileName).getFile();
		try(FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		    XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){
			
	        XSSFSheet firstSheet = workbook.getSheet(sheetName);
	        Iterator<Row> iterator = firstSheet.iterator();
	        if(iterator.hasNext()){
	        	Row nextRow = iterator.next();
	        	Iterator<Cell> cellIterator = nextRow.cellIterator(); 
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                headers.add(cell.getStringCellValue());
	            }
	        }
	        
	        for(int i=0;i<headers.size();i++){
	        	while(iterator.hasNext()){
	        		Row currentRow = iterator.next();
	        		Cell currentCell = currentRow.getCell(i);
	        		if(currentCell != null){
	        			Object value = getCellValueIrrespectiveOfType(currentCell);
	        			if(value != null){
	        				if(getCellValueIrrespectiveOfType(currentCell).toString().contains(FILE_NOTATION)){
			                	String embeddedFileName = getCellValueIrrespectiveOfType(currentCell).toString().substring(FILE_NOTATION.length());
			                	String embeddedFilePath = sDataPath.concat(embeddedFileName);
			                	JSONObject jsonContents = FileOperations.getFileContentsInJson(embeddedFilePath);
			                	value = jsonContents;
			                }
			                if(result != null && result.get(headers.get(i))!=null){
			                	if(!result.isEmpty() && !result.get(headers.get(i)).isEmpty() 
				                		&& !headers.get(i).isEmpty()){
				                	curValue = result.get(headers.get(i));	
				                }
			                }	                
			                curValue.add(value);
			                result.put(headers.get(i), curValue);
	        			}
		        		
	        		}	        		
	        	}
	        	curValue = new ArrayList<Object>();
	        	iterator = firstSheet.iterator();
	        	iterator.next();
	        }  	         
	        
		}catch(FileNotFoundException e){
			LOGGER.error("File not found:" + e.getMessage());
			throw new Error(e);	
		}catch(IOException e) {
			LOGGER.error("Error reading file:" + e.getMessage());
			throw new Error(e);
		}catch(NullPointerException e){
			LOGGER.error("Key don't exist or object don't exist at key:" + e.getMessage());
			throw new Error(e);
		}
		return result;
		
	}
	
	/**
	 * Get cell value from any cell irrespective of cell type
	 * @param cell
	 * @return
	 */
	public static Object getCellValueIrrespectiveOfType(Cell cell){
		 Object cellRecord = null;
 		 try{
 			switch (cell.getCellType()) 
 	         {
 	             case Cell.CELL_TYPE_NUMERIC:
 	                 cellRecord = cell.getNumericCellValue();
 	                 break;
 	             case Cell.CELL_TYPE_STRING:
 	            	 cellRecord = cell.getStringCellValue();
 	            	 break;
 	             case Cell.CELL_TYPE_BOOLEAN:
 	            	 cellRecord = cell.getBooleanCellValue();
 	         }
 		 }catch(IllegalStateException  e){
 			 LOGGER.error("Cell is having value in different format and retrieval is in different format:" + e.getMessage());
 			 throw new Error(e);
 		 }
		 
		 return cellRecord;
		 
	}
}
