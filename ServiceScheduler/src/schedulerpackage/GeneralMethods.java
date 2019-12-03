/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulerpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;

import org.json.JSONObject;

/**
 *
 * @author Saurish
 */
public class GeneralMethods {

    private String webserviceStatus;
    ReadProperty objReadProperty = null;
    GetSetPurchaseOrderData objGetSetPurchaseOrderData = null;

    public String populatePurchaseOrder(String purchaseorderno) {
        LogProcessing.settingLogFiles();

        objGetSetPurchaseOrderData = new GetSetPurchaseOrderData();
        objReadProperty = new ReadProperty();
        try {

            JSONObject request_json = new JSONObject();
            request_json.put("PONumber", purchaseorderno);
            String outputJSON = callWebService(
                    objReadProperty.getValue("getPOData"),
                    request_json.toString()
            );
            webserviceStatus = objGetSetPurchaseOrderData.parsePoOutputJSON(outputJSON);
            LogProcessing.jsonlogs.info("IsStatus return : " + webserviceStatus);
        } catch (Exception ex) {
            LogProcessing.logErrors.info("Exception Occured :: " + ex);

        }

        if (!webserviceStatus.equalsIgnoreCase("true")) {
            LogProcessing.logErrors.info("Exception at some point :: ");
        }
        return webserviceStatus;
    }

    public String populateGateEntry(String purchaseorderno, String invoiceno) throws IOException {
        System.out.println("Inside populateGateEntry");
        String outputJSON = "";
        try {
            JSONObject request_json = new JSONObject();
            request_json.put("PONumber", purchaseorderno);
            request_json.put("ChallanNumber", invoiceno);
            outputJSON = callWebService(
                    objReadProperty.getValue("getGateEntryData"),
                    request_json.toString()
            );
            GetSetGateEntryData objGetSetGateEntryData = new GetSetGateEntryData();
            webserviceStatus = objGetSetGateEntryData.parseGateEntryOutputJSON(outputJSON);
        } catch (JSONException ex) {
            LogProcessing.logErrors.info("JSON Error ::: " + ex);
        }
        if (!webserviceStatus.equalsIgnoreCase("true")) {
            LogProcessing.logErrors.info("Exception at some point :: ");
        }
        return webserviceStatus;
    }

    public static String callWebService(String serviceURL, String inputJSON) {
        String outputJSON = "";
        LogProcessing.jsonlogs.info("Input JSON.... " + inputJSON);
        try {
            URL url = new URL(serviceURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Authorization", "Basic OjEyMw==");
            OutputStream os = conn.getOutputStream();
            os.write(inputJSON.getBytes());
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            outputJSON = br.readLine();
            LogProcessing.jsonlogs.info("Output JSON.... " + outputJSON);
        } catch (MalformedURLException ex) {
            LogProcessing.logErrors.info("Exception Occured :: " + ex);
        } catch (IOException ex) {
            LogProcessing.logErrors.info("Exception Occured :: " + ex);
        }
        return outputJSON;
    }

}
