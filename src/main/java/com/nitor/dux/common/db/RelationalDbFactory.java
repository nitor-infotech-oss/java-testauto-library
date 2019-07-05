package com.nitor.dux.common.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelationalDbFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(RelationalDbFactory.class);
	
	public static IRelationalDbClient getClient(String dbClientName){
		switch(dbClientName.toLowerCase()){
			case "oracle":
				return new OracleDbClient();
			case "postgres":
				return new PostgresDbClient();
			default:
				return new OracleDbClient();
		}
		
	}
}
