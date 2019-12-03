/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulerpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.dmsapi.DMSXmlResponse;
import java.io.IOException;

/**
 *
 * @author AWCLappy170
 */
public class GetSetGateEntryData implements Serializable {

    String Query = null, Query1 = null, Query2 = null, listviewxml = null, inputxml = null, outputxml = null, sessionid = null, attri = null, processid = null;
    ResultSet rs = null, rs1 = null;
    static DMSXmlResponse xmlResponse;

    public String parseGateEntryOutputJSON(String content) throws JSONException, IOException {
        JSONObject objJSONObject = new JSONObject(content);

        String IsSuccess = objJSONObject.optString("IsSucceess");
        String ErrorMessage = objJSONObject.optString("ErrorMessage");
        if (IsSuccess.equalsIgnoreCase("true")) {
            attri = "<gateentryid>" + objJSONObject.optString("GateEntryId") + "</gateentryid>"
                    + "<lrno>" + objJSONObject.optString("LRNumber") + "</lrno>"
                    + "<lrdate>" + objJSONObject.optString("LRDate") + "</lrdate>"
                    + "<transportercode>" + objJSONObject.optString("TransporterCode") + "</transportercode>"
                    + "<transportername>" + objJSONObject.optString("TransporterName") + "</transportername>"
                    + "<vehicleno>" + objJSONObject.optString("VehicleNumber") + "</vehicleno>";
            JSONArray objJSONArray_GateLineContract = objJSONObject.getJSONArray("GateLineContract");
            for (int i = 0; i < objJSONArray_GateLineContract.length(); i++) {
                listviewxml = listviewxml + "<q_gateentrylines>"
                        + "<itemid>" + objJSONArray_GateLineContract.getJSONObject(i).optString("ItemId") + "</itemid>"
                        + "<itemname>" + objJSONArray_GateLineContract.getJSONObject(i).optString("ItemName") + "</itemname>"
                        + "<challanqty>" + objJSONArray_GateLineContract.getJSONObject(i).optString("VendorChallanQty") + "</challanqty>"
                        + "<grnqty>" + objJSONArray_GateLineContract.getJSONObject(i).optString("GRNQty") + "</grnqty>"
                        + "<wbfirwstwt>" + objJSONArray_GateLineContract.getJSONObject(i).optString("FirstWeight") + "</wbfirwstwt>"
                        + "<wbsecondwt>" + objJSONArray_GateLineContract.getJSONObject(i).optString("SecondWeight") + "</wbsecondwt>"
                        + "<wbnetweight>" + objJSONArray_GateLineContract.getJSONObject(i).optString("NetWeight") + "</wbnetweight>"
                        + "</q_gateentrylines>";

            }
        }
        LogProcessing.logAttributes.info("Gate Entry Data Attributes ::: " + attri + listviewxml);
        return attri + listviewxml;
    }

}
