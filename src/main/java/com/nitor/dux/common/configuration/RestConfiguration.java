package com.nitor.dux.common.configuration;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link RestConfiguration} to store any REST API related confgs. One specific property here is baseUri which is present for any REST API.
 * @author anup.manekar
 *
 */
public class RestConfiguration extends BaseConfiguration{

	private String baseUri;
	private final static Logger LOGGER = LoggerFactory.getLogger(RestConfiguration.class);
	
	public RestConfiguration(JSONObject configuration){
		super(configuration);
		try{			
			this.baseUri = this.getConfig().getString("BASEURI");			
		}catch(JSONException e){
			LOGGER.error("Error while setting REST configuration object:" + e.getMessage());
			throw new Error(e);
		}		
	}
	
	public String getBaseUri(){
		return baseUri;
	}
	
}
