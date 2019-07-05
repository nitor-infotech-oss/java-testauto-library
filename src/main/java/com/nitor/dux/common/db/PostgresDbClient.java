package com.nitor.dux.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is db client to connect to any Postgres DB
 * @author anup.manekar
 *
 */
public class PostgresDbClient implements IRelationalDbClient{

	private static final Logger LOGGER = LoggerFactory.getLogger(PostgresDbClient.class);
	private String hostName;
	private String port;
	private String userName;
	private String password;
	private String dbName;
	private String schema;
	private Connection conn;
	public ResultSet rs;
	
	/**
	 * Constructor to load PostgresDb driver
	 */
	public PostgresDbClient() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.error("Error: unable to load driver class" + e.getMessage());
			throw new Error(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void connect() {
		
		String url = "jdbc:postgresql://" + this.hostName + ":" + this.port + "/" + this.dbName;
		try {
			this.conn = DriverManager.getConnection(url,this.userName,this.password);
		} catch (SQLException e) {
			LOGGER.error("Error: unable connect:" + e.getMessage());
			throw new Error(e);
		}

	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean executeSelectQuery(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean executeUpdateQuery(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean executeDeleteQuery(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean executeInsertQuery(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}
	
}
