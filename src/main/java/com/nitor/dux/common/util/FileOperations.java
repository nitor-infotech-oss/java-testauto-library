package com.nitor.dux.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;;

/**
 * Defines methods to read *.json, *.xml, *.txt files and return content in form of {@link JSONObject} or {@link Document} 
 * as requested
 * @author anup.manekar
 *
 */
public class FileOperations {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileOperations.class);

	/**
	 * Return file contents in form of {@link JSONObject}
	 * @param fileName
	 * @return
	 */
	public static JSONObject getFileContentsInJson(String fileName) {
		String fileContents = null;
		fileContents = getFileContents(fileName);
		return new JSONObject(fileContents);	
		
	}
	
	/**
	 * Return file contents in form of {@link Document} 
	 * @param fileName
	 * @return
	 */
	public static Document getFileContentsInXml(String fileName){
		Document xmlObject = null;
		try{
			ClassLoader classLoader = FileOperations.class.getClassLoader();
			File inputFile = new File(classLoader.getResource(fileName).toURI().getPath());
	        DocumentBuilderFactory dbFactory 
	           = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        xmlObject = dBuilder.parse(inputFile);
	        xmlObject.getDocumentElement().normalize();
		}catch(ParserConfigurationException e){
			LOGGER.error("Error in parser configuration" + e.getMessage());
			throw new Error(e);
		}catch(SAXException e){
			LOGGER.error("Error while parsing document. Please check XML structure." + e.getStackTrace());
			throw new Error(e);
		}catch(IOException e){
			LOGGER.error("Error in file-" + fileName + " reading operation:" + e.getMessage());
			throw new Error(e);
		} catch (URISyntaxException e) {
			LOGGER.error("Error in file path:" + e.getMessage());
			throw new Error(e);
		}
		
		
		return xmlObject;
	}
	
	/**
	 * Return raw contents of file in form of String
	 * @param fileName
	 * @return
	 */
	public static String getFileContents(String fileName){
		
		String contents="";
		BufferedReader br = null;
		LOGGER.info("FilePathAndName:" + fileName);
		try {

			String sCurrentLine;
			ClassLoader classLoader = FileOperations.class.getClassLoader();
			LOGGER.info("Full Path:" + classLoader.getResource(fileName).toURI().getPath());
			br = new BufferedReader(new FileReader(classLoader.getResource(fileName).toURI().getPath()));

			while ((sCurrentLine = br.readLine()) != null) {
				contents = contents.concat(sCurrentLine);
			}			
			
		} catch (IOException e) {
			LOGGER.error("Error in file-" + fileName + " reading operation:" + e.getStackTrace());
			
		} catch(URISyntaxException e){
			LOGGER.error("Error in path syntax: " + fileName  + "\nStack Trace:" + e.getStackTrace());
		}finally {
			try {
				if (br != null)br.close();
			} catch (IOException e) {
				LOGGER.error("Error in closing buffered reader of file:" + fileName + "\nMessage:" +  e.getMessage());
			}
		}
		return contents;
		
		
	}
}
