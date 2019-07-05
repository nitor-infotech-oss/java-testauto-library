package com.nitor.dux.common.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class SoapService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SoapService.class);

	// The SOAP server URI
	private String soapUri;
	// The SOAP connection
	private SOAPConnection soapConnection;
	// If you want to add namespace to the header, follow this constant
	private static final String PREFIX_NAMESPACE = "ns";
	private static final String NAMESPACE = "http://other.namespace.to.add.to.header";
	
	public SoapService(String uri){
		this.soapUri = uri;
		try {
			this.createSOAPConnection();
		} catch (UnsupportedOperationException | SOAPException e) {
			LOGGER.error(e.toString());
		}
	}
	
	/**
	 * Create a SOAP connection
	 * 
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 */
	private void createSOAPConnection() throws UnsupportedOperationException,
			SOAPException {

		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory;
		soapConnectionFactory = SOAPConnectionFactory.newInstance();
		soapConnection = soapConnectionFactory.createConnection();
	}
	
	/**
	 * Send a SOAP request for a specific operation
	 * 
	 * @param xmlRequestBody
	 *            the body of the SOAP message
	 * @param operation
	 *            the operation from the SOAP server invoked
	 * @return a response from the server
	 */
	public String sendMessageToSOAPServer(String xmlRequestBody,
			String operation) {
		try{
			// Send SOAP Message to SOAP Server
			final SOAPElement stringToSOAPElement = this.stringToSOAPElement(xmlRequestBody);
			final SOAPMessage soapResponse = soapConnection.call(
					this.createSOAPRequest(stringToSOAPElement, operation),
					this.soapUri);

			// Print SOAP Response
			LOGGER.info("Response SOAP Message : " + soapResponse.toString());
			return soapResponse.toString();
		}catch(SOAPException e){
			LOGGER.error(e.getMessage());
			throw new Error(e);
		}
		
	}
	
	/**
	 * Create a SOAP request
	 * 
	 * @param body
	 *            the body of the SOAP message
	 * @param operation
	 *            the operation from the SOAP server invoked
	 * @return the SOAP message request completed
	 */
	private SOAPMessage createSOAPRequest(SOAPElement body, String operation) {
		try{
			final MessageFactory messageFactory = MessageFactory.newInstance();
			final SOAPMessage soapMessage = messageFactory.createMessage();
			final SOAPPart soapPart = soapMessage.getSOAPPart();

			// SOAP Envelope
			final SOAPEnvelope envelope = soapPart.getEnvelope();
			envelope.addNamespaceDeclaration(PREFIX_NAMESPACE, NAMESPACE);

			// SOAP Body
			final SOAPBody soapBody = envelope.getBody();
			soapBody.addChildElement(body);

			// Mime Headers
			final MimeHeaders headers = soapMessage.getMimeHeaders();
			LOGGER.info("SOAPAction : " + this.soapUri + operation);
			headers.addHeader("SOAPAction", this.soapUri + operation);

			soapMessage.saveChanges();

			/* Print the request message */
			LOGGER.info("Request SOAP Message :" + soapMessage.toString());
			return soapMessage;
		}catch(SOAPException e){
			LOGGER.error(e.getMessage());
			throw new Error(e);
		}
		
	}

	/**
	 * Transform a String to a SOAP element
	 * 
	 * @param xmlRequestBody
	 *            the string body representation
	 * @return a SOAP element
	 */
	private SOAPElement stringToSOAPElement(String xmlRequestBody){
	
		try{
			// Load the XML text into a DOM Document
			final DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			builderFactory.setNamespaceAware(true);
			final InputStream stream = new ByteArrayInputStream(
					xmlRequestBody.getBytes());
			final Document doc = builderFactory.newDocumentBuilder().parse(stream);
		
			// Use SAAJ to convert Document to SOAPElement
			// Create SoapMessage
			final MessageFactory msgFactory = MessageFactory.newInstance();
			final SOAPMessage message = msgFactory.createMessage();
			final SOAPBody soapBody = message.getSOAPBody();	

			// This returns the SOAPBodyElement that contains ONLY the Payload
			return soapBody.addDocument(doc);
			
		} catch(SAXException e){
			LOGGER.error(e.getMessage());
			throw new Error(e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new Error(e);
		} catch (ParserConfigurationException e) {
			LOGGER.error(e.getMessage());
			throw new Error(e);
		} catch (SOAPException e) {
			LOGGER.error(e.getMessage());
			throw new Error(e);
		}
		
	}
	
}
