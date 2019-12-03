package com.wcl.workitemprocessing;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.JSONObject;

import com.wcl.commonmethods.Methods;
import com.wcl.main.LoadConnections;
import com.wcl.main.LoadLogs;

public class StartWorkitemProcessing {

	public void completePendingWI() {

		try {
			Methods objMethods = new Methods();
			ArrayList<String> PendigPID = new ArrayList<>();
			String Query_getPendingWI = "SELECT ProcessInstanceID FROM WFINSTRUMENTTABLE WHERE ProcessDefID = '"
					+ LoadConnections.getProperty("ProcessDefID") + "' AND ActivityId = '"
					+ LoadConnections.getProperty("ActivityId") + "'";
			ResultSet RS_getPendingWI = objMethods.executeQuery(Query_getPendingWI);
			int rowcount = 0;
			while (RS_getPendingWI.next()) {
				PendigPID.add(RS_getPendingWI.getString(1));
				rowcount++;
			}

			if (rowcount == 0) {
				LoadLogs.Summary.info("Zero pendig workitem in queue. Shutting down..");
				System.exit(0);
			} else {
				for (int i = 0; i < PendigPID.size(); i++) {
					String PID = PendigPID.get(i);
					String Query_pidDetails = "SELECT purchaseorderno,invoiceno FROM ext_supplypoinvoices WHERE processid ='"
							+ PID + "'";
					ResultSet RS_pidDetails = objMethods.executeQuery(Query_pidDetails);					
					if(RS_pidDetails.first()) {						
						PendigPID.add(RS_pidDetails.getString(1));
						JSONObject request_json = new JSONObject();
						request_json.put("PONumber", RS_pidDetails.getString(1));
						request_json.put("PackingSlipId", RS_pidDetails.getString(2));						
						String outputJSON = objMethods.callWebService(LoadConnections.getProperty(""), request_json.toString());
					}					
				}
			}

		} catch (Exception e) {
			LoadLogs.Error.info("Exception while fetching pending workitems");
			e.printStackTrace();
		}
	}
}
