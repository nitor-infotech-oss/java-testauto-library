package com.nitor.dux.common.service;

/**
 * Interface for any service client implementation
 * @author anup.manekar
 *
 */
public interface IServiceClient {

	/**
	 * Send request
	 * @return
	 */
	public boolean sendRequest();
	
	/**
	 * Get response objects
	 * @return
	 */
	public Object getResponse();
}
