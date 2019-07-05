package com.nitor.dux.common.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Defines methods to do perform various actions on XML document
 * @author anup.manekar
 *
 */
public class XmlParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlParser.class);

	/**
	 * Return {@link Document} object for xml string supplied
	 * @param xml
	 * @return
	 */
	public static Document getXmlObjectFromString(String xml){
		Document xmlObject = null;
		try{
			LOGGER.debug("Instantiating DocumentBuilderFactory");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			LOGGER.debug("Parsing xml string:" + xml);
			InputSource is = new InputSource(new StringReader(xml));
			xmlObject= dBuilder.parse(is);
			LOGGER.debug("Normalizing xml string:" + xmlObject.toString());
			xmlObject.getDocumentElement().normalize();
		}catch(ParserConfigurationException e){
			LOGGER.error("Error in parser configuration" + e.getMessage());
			throw new Error(e);
		}catch(SAXException e){
			LOGGER.error("Error while parsing document. Please check XML structure." + e.getStackTrace());
			throw new Error(e);
		}catch(IOException e){
			LOGGER.error("Error in reading operation:" + e.getMessage());
			throw new Error(e);
		}
		
		
		return xmlObject;
	}
}
