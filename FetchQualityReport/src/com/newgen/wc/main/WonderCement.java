/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.wc.main;

import com.newgen.wc.beanclasses.BeanClass;
import com.newgen.wc.beanclasses.SXAQORMContractList;
import com.newgen.wc.beanclasses.SXAQORMGRNContractList;
import com.newgen.wc.conf.ConfProperty;
import com.newgen.wc.conf.LogProcessing;
import com.newgen.wc.dataparsing.Parsing;
import com.newgen.wc.workintroduction.ConnectDisconnectFlow;
import com.newgen.wc.workintroduction.UploadWInFlow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import org.json.JSONObject;

/**
 *
 * @author Anchal Dua
 */
public class WonderCement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String pid = "";
            System.out.println("Initiating process sequence.");
           // LogProcessing.settingLogFiles();
          
//@ Getting property file's path 
           // String currentdir = System.getProperty("user.dir");
            String finaldir = "E:\\Newgen\\Server\\jboss-eap-6.4\\bin\\WonderCement" + File.separator + "conf" + File.separator + "conf.properties";
            InputStream is = new FileInputStream(finaldir);
            Properties prop = new Properties();
            prop.load(is);
            String qualityOrderUrl = prop.getProperty("getqualityorderforrm");
            System.out.println("property file's path "+qualityOrderUrl);

//@ input json
            JSONObject request_json = new JSONObject();
            request_json.put("PONumber", "102PO1906101");
            request_json.put("PackingSlipId", "128/19");
            String outputJSON = callWebService(qualityOrderUrl, request_json.toString());

//@ Parsing Data
            BeanClass beanclass = null;
            Parsing parse = new Parsing();
            beanclass = parse.parsingjson(outputJSON);
            System.out.println("parsing done");

// 1.) Create connection with Data Base 
            ConfProperty confprop = new ConfProperty();
            Connection con = null;
            con = confprop.createConnection();
            System.out.println("connection has done with DB"+con);

// 2.) Execute newgen connect cabinet call  
                 try {
            ConnectDisconnectFlow conn = new ConnectDisconnectFlow();
            String sessionId = conn.connectFlow();
            System.out.println("Session ID ::: " + sessionId);

            UploadWInFlow upload = new UploadWInFlow();
            pid = upload.uploadWI(sessionId);

            System.out.println("PID " + pid);
            } catch (Exception e) {
                e.printStackTrace();
            }

// 3.) Execute insert query in respective tables 
            Statement stmt = null;
            stmt = con.createStatement();
            System.out.println("inserting query");
            stmt.executeUpdate("INSERT INTO cmplx_qomaster (fromdate,itemid,itemname,orderstatus,qodate,qty,qualityorderid,site,source,testgroup,todate,transporterid,transportername,vendorid,vendorname) "
                    + "VALUES (" + beanclass.getFromDate() + "," + beanclass.getItemId() + "," + beanclass.getItemName() + "," + beanclass.getOrderStatus() + "," + beanclass.getqODate() + "," + beanclass.getQty() + "," + beanclass.getQualityOrderID() + "," + beanclass.getSite() + "," + beanclass.getSource() + "," + beanclass.getTestGroup() + "," + beanclass.getToDate() + "," + beanclass.getTransporterId() + "," + beanclass.getTransporterName() + "," + beanclass.getVendorId() + "," + beanclass.getVendorName() + ")");
            System.out.println("first query done");
            SXAQORMContractList list1 = new SXAQORMContractList();
            SXAQORMGRNContractList list2 = new SXAQORMGRNContractList();
            JSONObject obj = new JSONObject(outputJSON);
//@ inserting data into first array list's table  
            org.json.JSONArray arrayList1 = obj.getJSONArray("SXAQORMContractList");
            for (int i = 0; i < arrayList1.length(); i++) {
                stmt.execute("INSERT INTO cmplx_qatestresultmapping (maxvalue,minvalue,standardvalue,testid,testqty,qualityorderid) "
                        + "VALUES (" + list1.getMaxValue() + "," + list1.getMinValue() + "," + list1.getStandardValue() + "," + list1.getTestId() + "," + list1.getTestQty() + "," + beanclass.getQualityOrderID() + ")");
            }
               System.out.println("second query done");
//@ inserting data into second array list's table
            org.json.JSONArray arrayList2 = obj.getJSONArray("SXAQORMGRNContractList");
            for (int i = 0; i < arrayList2.length(); i++) {
                stmt.execute("INSERT INTO cmplx_qogrnmapping (ponumber,packingslipid,qualityorderid) "
                        + "VALUES (" + list2.getPONumber() + "," + list2.getPackingSlipId() + "," + beanclass.getQualityOrderID() + ")");
            }
            System.out.println("query inserted");
// 4.)Complete WI call
          //  upload.completeWI(pid, sessionId);

        } catch (Exception e) {
          //  LogProcessing.printConsoleLog("Error in processing: " + e.getMessage());
            e.printStackTrace();
            System.out.println(""+e);
            System.out.println("Oops!! exception occurs. Halting Utility.");
        }

       // LogProcessing.dumpFinalLogs();
    }
//@  calling webservice for output
    public static String callWebService(String serviceURL, String inputJSON) {
        String outputJSON = "";
        try {
            System.out.println("Inside callWebService");
            System.out.println("serviceURL = " + serviceURL);
            System.out.println("inputJSON = " + inputJSON);
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
            System.out.println("Output from WebServcie .... " + outputJSON);
        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }
        return outputJSON;
    }

}
