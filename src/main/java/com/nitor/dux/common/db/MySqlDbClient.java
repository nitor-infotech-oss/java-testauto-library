package com.nitor.dux.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is db client to connect to MySQL databases
 * @author anup.manekar
 *
 */
public class MySqlDbClient implements IRelationalDbClient {

	private String hostName;
	private String port;
	private String userName;
	private String password;
	private String dbName;
	private String dbURL;
	private Connection conn;
	public ResultSet rs;
	public java.sql.Statement stmt;
	private final static Logger LOGGER = LoggerFactory.getLogger(MySqlDbClient.class);

	/**
	 * Constructor to set db properties
	 * @param sDbUser
	 * @param sDbPassword
	 * @param sDbHost
	 * @param sDbPort
	 * @param sDbName
	 */
	public MySqlDbClient(String sDbUser, String sDbPassword, String sDbHost, String sDbPort,
			String sDbName) {
		this.dbName = sDbName;
		this.port = sDbPort;
		this.hostName = sDbHost;
		this.userName = sDbUser;
		this.password = sDbPassword;
		this.connect();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect() {
		try {
			this.dbURL = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
			java.sql.Connection con = DriverManager.getConnection(dbURL, userName, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			LOGGER.error("Exception in method connect: " + e.getMessage());
			throw new Error(e);
		}
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
		try {
			if (this.conn != null) {
				this.conn.close();
			}
		} catch (SQLException e) {
			LOGGER.error("Exception in method closeConnection: " + e.getMessage());
			throw new Error(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean executeSelectQuery(String query) {
		// TODO Auto-generated method stub
		try {
			this.rs = this.stmt.executeQuery(query);
		} catch (SQLException e) {
			LOGGER.error("Exception in method executeSelectQuery: " + e.getMessage());
			throw new Error(e);
		}finally{
			return (this.rs != null) ? true : false;
		}
		
	}
}
