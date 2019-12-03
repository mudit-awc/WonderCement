package com.wcl.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoadConnections {

	public static Connection WonderDBConn = null;
	static String sid = "";
	static String dbIP = "";
	static String dbDriverClass = "";
	static String dbDriverSource = "";
	static String username = "";
	static String password = "";

	public static void createDBConnection() {
		try {

			LoadConnections.sid = InitiateProcessing.WCLProperty.getProperty("DatabaseName");
			LoadConnections.dbIP = InitiateProcessing.WCLProperty.getProperty("DatabaseIP");
			LoadConnections.dbDriverClass = InitiateProcessing.WCLProperty.getProperty("DatabaseDriverClass");
			LoadConnections.dbDriverSource = InitiateProcessing.WCLProperty.getProperty("DatabaseDriverSource");
			LoadConnections.username = InitiateProcessing.WCLProperty.getProperty("UserName");
			LoadConnections.password = InitiateProcessing.WCLProperty.getProperty("Password");

			System.out.println("SID " + sid);
			System.out.println("dbIP " + dbIP);
			System.out.println("dbDriverClass " + dbDriverClass);
			System.out.println("dbDriverSource " + dbDriverSource);
			System.out.println("username " + username);
			System.out.println("password " + password);

			Class.forName(dbDriverClass);

			// reset newgen connection if exists
			try {
				if (WonderDBConn != null) {
					WonderDBConn.close();
					WonderDBConn = null;
				}

			} catch (SQLException se) {
				LoadLogs.Error.info("Exception in reseting DB connection :" + se.getMessage());
				return;
			}

			// creating Newgen connection
			do {
				try {
					WonderDBConn = DriverManager.getConnection(dbDriverSource, username, password);
				} catch (SQLException sqle) {
					LoadLogs.Error.info("Error in connecting DB database. " + sqle.getMessage());
					WonderDBConn = null;
				}

				if (WonderDBConn == null) {
					LoadLogs.Error.info("Waiting for newgen connection...");
					Thread.sleep(5000);
				} else {
					LoadLogs.Error.info("Re-connected with newgen database. ");
				}
			} while (WonderDBConn == null);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			LoadLogs.Error.info("Error While making connection with database.", cnfe);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			LoadLogs.Error.info("Error While making connection with database.", e);
			return;
		}
	}

	public static String getProperty(String key) throws Exception {
		String val = null;
		val = InitiateProcessing.WCLProperty.getProperty(key.toLowerCase().trim());
		if (val == null) {
			throw new Exception("No value found for Key:" + key.toLowerCase().trim());
		} else {
			return val;
		}
	}
}
