package com.nitor.dux.common.configuration;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link BaseConfiguration} object to store custom-properties/system-properties of any automation channel viz REST, UI, DB. This is base object and 
 * other configuraiton objects can be derived from this and store any specific configurations.
 * @author anup.manekar
 *
 */
public class BaseConfiguration {

	private JSONObject config;
	private final static Logger LOGGER = LoggerFactory.getLogger(BaseConfiguration.class);
	
	public BaseConfiguration(JSONObject configuration){
		LOGGER.debug("constructor called");
		this.setConfig(configuration);
		LOGGER.debug("constructor completed");		
	}

	public JSONObject getConfig() {
		LOGGER.debug("Get config object");
		return config;
	}

	public void setConfig(JSONObject config) {
		LOGGER.debug("Setting configuration object");
		this.config = config;
	}
	
}
