package com.nitor.dux.common.db;

/**
 * Interface to declare any relational DB operations
 * @author anup.manekar
 *
 */
public interface IRelationalDbClient {

	/**
	 * Connecting db client to DB
	 */
	void connect();

	/**
	 * Execute SQL query and return whether operation was successful. if operation is successful, then user will 
	 * retrieve result sets from the db client objects.
	 * @param query
	 * @return
	 */
	boolean executeSelectQuery(String query);

	/**
	 * Execute update query and return whether operation was successful or not. if operation is successful, then user will 
	 * retrieve result sets from the db client objects.
	 * @param query
	 * @return
	 */
	boolean executeUpdateQuery(String query);

	/**
	 * Execute delete query and return whether operation was successful or not.if operation is successful, then user will 
	 * retrieve result details from the db client objects.
	 * @param query
	 * @return
	 */
	boolean executeDeleteQuery(String query);

	/**
	 * Execute insert query and return whether operation was successful or not. if operation is successful, then user will 
	 * retrieve result details from the db client objects.
	 * @param query
	 * @return
	 */
	boolean executeInsertQuery(String query);

	/**
	 * Common function to close resultset, statement and connection objects
	 */
	void closeConnection();

}
