package com.wcl.commonmethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wcl.main.LoadConnections;
import com.wcl.main.LoadLogs;

public class Methods {

	public ResultSet executeQuery(String queryToExecute) throws SQLException {
		ResultSet rs = null;
		Statement stmt = LoadConnections.WonderDBConn.createStatement();
		try {
			rs = stmt.executeQuery(queryToExecute);
		} catch (SQLException sqle) {
			LoadLogs.Error.info(queryToExecute, sqle);
			LoadLogs.Error.info("Error While executing query :" + queryToExecute + " . Error:" + sqle.getMessage());
			throw new SQLException("Error While executing query :" + queryToExecute + " . Error:" + sqle.getMessage());
		}
		return rs;
	}

	public int executeUpdate(Statement stmt, String queryToExecute) throws SQLException {
		int count = -1;
		try {
			count = stmt.executeUpdate(queryToExecute);
		//	LogProcessing.statusLogs.debug("Returned Row Count :" + count + " , Query Executed:" + queryToExecute);
		} catch (SQLException sqle) {
			LoadLogs.Error.info(queryToExecute, sqle);
			LoadLogs.Error.info("Error While executing query :" + queryToExecute + " . Error:" + sqle.getMessage());
			throw new SQLException("Error While executing query :" + queryToExecute + " . Error:" + sqle.getMessage());
		}
		return count;
	}

	public static String callWebService(String serviceURL, String inputJSON) {
		String outputJSON = "";
		try {
			LoadLogs.Json.info("serviceURL.. " + serviceURL);
			LoadLogs.Json.info("InputJSON.. " + inputJSON);
			URL url = new URL(serviceURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(inputJSON.getBytes());
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			outputJSON = br.readLine();
			LoadLogs.Json.info("OutputJSON.. " + outputJSON);
		} catch (MalformedURLException ex) {
			LoadLogs.Error.info("Exception in calling web service : " + ex.getMessage());
		} catch (IOException ex) {

		}
		return outputJSON;
	}

}
