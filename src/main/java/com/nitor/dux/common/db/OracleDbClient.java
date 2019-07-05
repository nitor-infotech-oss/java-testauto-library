package com.nitor.dux.common.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is db client to connect to any Oracle DBs
 * @author anup.manekar
 *
 */
public class OracleDbClient implements IRelationalDbClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(OracleDbClient.class);
	private String hostName;
	private String port;
	private String userName;
	private String password;
	private String dbName;
	private String schema;
	private Connection conn;
	private CallableStatement pstmt;
	private Statement stmt;
	public ResultSet rs;

	/**
	 * Constructor to set all relevant properties
	 * @param hostName
	 * @param port
	 * @param userName
	 * @param password
	 * @param dbName
	 * @param schema
	 */
	public OracleDbClient(String hostName, String port, String userName, String password, String dbName, String schema) {
		this.hostName = hostName;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.dbName = dbName;
		this.schema = schema;
		this.rs = null;
		this.pstmt = null;
		this.stmt = null;
		this.connect();
	}

	/**
	 * Constructor to load Oracle driver
	 */
	public OracleDbClient() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
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
		String url = "jdbc:oracle:thin:" + this.userName + "/" + this.password + "@" + this.hostName + ":" + this.port + ":" + this.dbName;
		try {
			this.conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			LOGGER.error("Error while connecting OracleDB:" + e.getMessage());
			throw new Error(e);
		}

	}

	/**
	 * Method to execute stored procedure
	 * @param proc
	 * @param inParams
	 * @param outParams
	 * @return
	 */
	public String[] executeStoredProcedure(String proc, String[] inParams, String[] outParams) {
		String[] output = new String[outParams.length];
		try {
			this.pstmt = this.conn.prepareCall(proc);
			for (int i = 0; i < inParams.length; i++) {
				this.pstmt.setString(i + 1, inParams[i]);
			}
			for (int i = 0; i < outParams.length; i++) {
				this.pstmt.registerOutParameter(inParams.length + i + 1, java.sql.Types.VARCHAR);
			}
			this.pstmt.executeUpdate();
			for (int i = 0; i < outParams.length; i++) {
				output[i] = this.pstmt.getString(inParams.length + i + 1);
			}

		} catch (SQLException e) {
			LOGGER.error("Error executing stored procedure " + e.getMessage());
			throw new Error(e);
		} finally{
			this.closeConnection();
		}
		return output;

	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean executeSelectQuery(String query) {
		try {
			this.stmt = this.conn.createStatement();
			this.rs = this.stmt.executeQuery(query);
		} catch (SQLException e) {
			LOGGER.error("Error executing select query:" + e.getMessage());
			throw new Error(e);
		}finally{
			this.closeConnection();
		}
		return (this.rs != null) ? true : false;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean executeUpdateQuery(String query) {
		int rowCount = 0;
		try {
			this.stmt = this.conn.createStatement();
			rowCount = this.stmt.executeUpdate(query);
		} catch (SQLException e) {
			LOGGER.error("Error executing update/delete/insert query:" + e.getMessage());
			throw new Error(e);
		} finally{
			this.closeConnection();
		}
		return (rowCount > 0) ? true : false;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean executeDeleteQuery(String query) {
		return executeUpdateQuery(query);

	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public boolean executeInsertQuery(String query) {
		return executeUpdateQuery(query);

	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void closeConnection() {
		try {
			if (this.rs != null && !this.rs.isClosed())
				this.rs.close();
			if (this.stmt != null && !this.stmt.isClosed())
				this.stmt.close();
			if (this.pstmt != null && !this.pstmt.isClosed())
				this.pstmt.close();
			if (!this.conn.isClosed())
				this.conn.close();
		} catch (SQLException e) {
			LOGGER.error("Error closing DB connection:" + e.getMessage());
			throw new Error(e);
		}

	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}