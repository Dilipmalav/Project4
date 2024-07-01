package com.rays.pro4.Exception;

/**
 * DatabaseException is propogated by DAO classes when an unhandled Database
 * exception occurred.
 * 
 * @author Dilip Malav
 *
 */

public class DatabaseException extends Exception {

	public DatabaseException(String msg){
		super(msg);
	}
	
}
