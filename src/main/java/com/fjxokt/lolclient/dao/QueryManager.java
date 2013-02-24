package com.fjxokt.lolclient.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryManager {

	private static QueryManager instance;
	
	private Connection connection;
	
	private QueryManager() {
		// load the sqlite-JDBC driver using the current class loader
	    try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
		}
	}
	
	public static QueryManager getInst() {
		if (instance == null) {
			instance = new QueryManager();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public Statement createStatement() {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
		}
		return null;
	}
	
	public ResultSet executeQuery(String sql) {
		try {
			Statement st = connection.createStatement();
			return st.executeQuery(sql);
		} catch (SQLException e) {
		}
		return null;
	}
	
	public void load(String filename) {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (SQLException e) {
		}
	}
	
	public void close() {
		if (connection != null) {
	          try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}
	
}
