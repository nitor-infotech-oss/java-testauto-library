package com.nitor.dux.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RestService is client class to do all REST operations.
 * @author anup.manekar
 *
 */
public class RestService implements IServiceClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestService.class);
	
	private RequestType requestType;
	private String relativePath;
	private HashMap<String,String> queryParams;
	private String body;
	private HashMap<String,String> headers;
	private String baseUri;
	private Response responseObj;
	private String responseBody;	
	private MediaType contentType;
	
	public MediaType getContentType() {
		return contentType;
	}

	public void setContentType(MediaType contentType) {
		this.contentType = contentType;
	}

	/**
	 * Reset is done so that same object with same baseurl can be used to do different operations. While doing different 
	 * operations with same object, all the previous values have to be trashed.
	 */
	public void reset(){
		this.requestType=null;
		this.relativePath="";
		this.queryParams=null;
		this.body = "";
		this.headers = null;
		this.responseObj = null;
		this.responseBody = null;
	}
	
	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public String getBaseUri() {
		return baseUri;
	}

	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		LOGGER.debug("Setting request type:" + requestType);
		this.requestType = requestType;
		LOGGER.debug("Request Type set:" + this.requestType.toString());
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		LOGGER.debug("Set Relative Path:" + relativePath);
		this.relativePath = relativePath;
		LOGGER.debug("Relative Path set:" + relativePath);
	}

	public HashMap<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(HashMap<String, String> queryParams) {
		LOGGER.debug("Set Query Params:" + queryParams);
		this.queryParams = queryParams;
		LOGGER.debug("Query params set:" + this.queryParams.toString());
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		LOGGER.debug("Setting body:" + body);
		this.body = body;
		LOGGER.debug("Body set:" + this.body);
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		LOGGER.debug("Setting headers:" + headers.toString());
		this.headers = headers;
		LOGGER.debug("Headers set:" + this.headers.toString());
	}
	
	/**
	 * {@inheritDoc}
	 * Based on HTTP method, relevant methods are invoked
	 */
	public boolean sendRequest() {

		switch(this.requestType){
			case GET:
				return sendGetRequest();
			case POST:
				return sendPostRequest();
			case PUT:
				return sendPutRequest();
			case DELETE:
				return sendDeleteRequest();
		}
		
		return false;
	}
	
	/**
	 * Invoke PUT request and return boolean parameter depending on success
	 * @return
	 */
	public boolean sendPutRequest(){
		this.responseObj = null;
		WebTarget target = ClientBuilder.newClient().target(this.baseUri).path(this.relativePath);
		if(this.queryParams != null){
			for (Map.Entry<String, String> entry : this.queryParams.entrySet()) {
				target = target.queryParam(entry.getKey(), entry.getValue());
			}
		}				
		
		Invocation.Builder invocationBuilder = target.request(this.contentType);
		if(this.headers != null){
			for (Map.Entry<String, String> entry : this.headers.entrySet()) {
				invocationBuilder = invocationBuilder.header(entry.getKey(), entry.getValue());
			}
		}
				
		this.responseObj = invocationBuilder.put(Entity.entity(this.body, this.contentType));
		LOGGER.debug("Response retrieved");
		LOGGER.debug("Response Status:" + this.responseObj.getStatus());
		LOGGER.debug("Response Content Length:" + this.responseObj.getLength());
		if(this.responseObj != null)
			this.responseBody = responseObj.readEntity(String.class);
		LOGGER.debug("Response Content:" + this.responseBody);
		return (this.responseObj == null)?false:true;
	}
	
	/**
	 * Invoke POST request and return boolean parameter depending on success
	 * @return
	 */
	public boolean sendPostRequest() {	
		this.responseObj = null;
		WebTarget target = ClientBuilder.newClient().target(this.baseUri).path(this.relativePath);
		if(this.queryParams != null){
			for (Map.Entry<String, String> entry : this.queryParams.entrySet()) {
				target = target.queryParam(entry.getKey(), entry.getValue());
			}
		}				
		
		Invocation.Builder invocationBuilder = target.request(this.contentType);
		if(this.headers != null){
			for (Map.Entry<String, String> entry : this.headers.entrySet()) {
				invocationBuilder = invocationBuilder.header(entry.getKey(), entry.getValue());
			}
		}
				
		this.responseObj = invocationBuilder.post(Entity.entity(this.body, this.contentType));
		LOGGER.debug("Response retrieved");
		LOGGER.debug("Response Status:" + this.responseObj.getStatus());
		LOGGER.debug("Response Content Length:" + this.responseObj.getLength());
		if(this.responseObj != null)
			this.responseBody = responseObj.readEntity(String.class);
		LOGGER.debug("Response Content:" + this.responseBody);
		return (this.responseObj == null)?false:true;
		
	}
	
	/**
	 * Invoke GET request and return boolean parameter depending on success
	 * @return
	 */
	public boolean sendGetRequest(){
		try{
			this.responseObj = null;		
			WebTarget target = ClientBuilder.newClient().target(this.baseUri).path(this.relativePath);
			if(this.queryParams != null){
				for (Map.Entry<String, String> entry : this.queryParams.entrySet()) {
					target = target.queryParam(entry.getKey(), entry.getValue());
				}
			}				
			
			Invocation.Builder invocationBuilder = target.request(this.contentType);
			if(this.headers != null){
				for (Map.Entry<String, String> entry : this.headers.entrySet()) {
					invocationBuilder = invocationBuilder.header(entry.getKey(), entry.getValue());
				}
			}	
			this.responseObj = invocationBuilder.get();
			LOGGER.debug("Response retrieved");
			LOGGER.debug("Response Status:" + this.responseObj.getStatus());
			LOGGER.debug("Response Content Length:" + this.responseObj.getLength());
			if(this.responseObj != null)
				this.responseBody = responseObj.readEntity(String.class);
			LOGGER.debug("Response Content:" + this.responseBody);	
		}catch(Exception e){
			LOGGER.error("Error in sending GET request:" + e.getStackTrace());
		}
		return (this.responseObj == null)?false:true;
		
	}
	
	/**
	 * Invoke DELETE request and return boolean parameter depending on success
	 * @return
	 */
	public boolean sendDeleteRequest(){
		try{
			this.responseObj = null;		
			WebTarget target = ClientBuilder.newClient().target(this.baseUri).path(this.relativePath);
			if(this.queryParams != null){
				for (Map.Entry<String, String> entry : this.queryParams.entrySet()) {
					target = target.queryParam(entry.getKey(), entry.getValue());
				}
			}				
			
			Invocation.Builder invocationBuilder = target.request(this.contentType);
			if(this.headers != null){
				for (Map.Entry<String, String> entry : this.headers.entrySet()) {
					invocationBuilder = invocationBuilder.header(entry.getKey(), entry.getValue());
				}
			}	
			this.responseObj = invocationBuilder.delete();
			LOGGER.debug("Response retrieved");
			LOGGER.debug("Response Status:" + this.responseObj.getStatus());
			LOGGER.debug("Response Content Length:" + this.responseObj.getLength());
			if(this.responseObj != null)
				this.responseBody = responseObj.readEntity(String.class);
			LOGGER.debug("Response Content:" + this.responseBody);
				
		}catch(Exception e){
			LOGGER.error("Error in sending DELETE request:" + e.getStackTrace());
		}
		return (this.responseObj == null)?false:true;
		
	}

	/**
	 * Return raw response object
	 */
	public Response getResponse() {
		// TODO Auto-generated method stub
		return this.responseObj;
	}
	
	public void constructRequest(){
		
	}

}
