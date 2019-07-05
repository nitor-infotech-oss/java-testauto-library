package com.nitor.dux.common.configuration;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link DbConfiguration} to store any db related properties
 * @author anup.manekar
 *
 */
public class DbConfiguration extends BaseConfiguration{
	private String dbType;
	private String host;
	private String port;
	private String dbName;
	private String username;
	private String password;
	private String schema;
	private final static Logger LOGGER = LoggerFactory.getLogger(DbConfiguration.class);
	
	public DbConfiguration(JSONObject config){
		super(config);
		
	}
}
