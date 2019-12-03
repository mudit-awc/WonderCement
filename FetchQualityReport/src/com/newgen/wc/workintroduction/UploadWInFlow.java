/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.wc.workintroduction;

//import ISPack.ISUtil.JPISException;
import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.dmsapi.DMSXmlResponse;
import com.newgen.wc.conf.ConfProperty;
import static com.newgen.wc.conf.ConfProperty.prop;
import static com.newgen.wc.workintroduction.ConnectDisconnectFlow.serverIP;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Anchal Dua
 */
public class UploadWInFlow {
     
     String defid = "";
     String uploadFlag = "";
   
   public String uploadWI(String sessionID) {
      //  LogProcessing.statusLogs.info("Initiating workitem upload for sBarcodeNo :" + FILE_NO);
        //LogProcessing.summaryLogs.info("Initiating workitem upload for sBarcodeNo :" + barCodeNo);
        String processInstanceID = null;
        DMSXmlResponse xmlResponse = null;
        StringBuffer inputXml = new StringBuffer();
         String inputXml2=null;
        String outputXml = null;
        String mainCode = null;
        String folderIndex = null;
        //String documents = uploadAPPDoc(sessionID, FILE_NO, volumeId);
        defid = prop.getProperty("SupplyPoInvoicesDefID");
      
        //ConnectDisconnectFlow cdf = new ConnectDisconnectFlow();
        //sessionID = cdf.connectFlow();
        try {

            inputXml.append("<WFUploadWorkItem_Input>");
            inputXml.append("<Option>WFUploadWorkItem</Option>");
            inputXml.append("<EngineName>" + ConnectDisconnectFlow.engineName + "</EngineName>");
            inputXml.append("<SessionId>" + sessionID + "</SessionId>");
            inputXml.append("<ProcessDefId>" + defid + "</ProcessDefId>");
            inputXml.append("<InitiateFromActivityId></InitiateFromActivityId>");
            inputXml.append("<DataDefName></DataDefName>");
            inputXml.append("<InitiateAlso>Y</InitiateAlso>");
            inputXml.append("<UserDefVarFlag>Y</UserDefVarFlag>");
            inputXml.append("<SynchronousRouting>Y</SynchronousRouting>");
            inputXml.append("<Fields></Fields>");
            //inputXml.append("<Documents>" + documents + "</Documents>");
            inputXml.append("<Documents></Documents>");

            inputXml.append("<Attributes>" + "" + "</Attributes>");
            inputXml.append("</WFUploadWorkItem_Input>");
            
            
//              inputXml2= "<WFUploadWorkItem_Input>"
//                     +"<Option>WFUploadWorkItem</Option>"
//             +"<Option>WFUploadWorkItem</Option>"
//           +"<EngineName>" + ConnectDisconnectFlow.engineName + "</EngineName>"
//           +"<SessionId>" + sessionID + "</SessionId>"
//           +"<ProcessDefId>" + "processDefID" + "</ProcessDefId>"
//           +"<InitiateFromActivityId></InitiateFromActivityId>"
//           +"<DataDefName></DataDefName>"
//           +"<InitiateAlso>Y</InitiateAlso>"
//           +"<UserDefVarFlag>Y</UserDefVarFlag>"
//           +"<SynchronousRouting>Y</SynchronousRouting>"
//           +"<Fields></Fields>"
//           +"<Documents></Documents>"
//           +"<Attributes>" + "" + "</Attributes>"
//           +"</WFUploadWorkItem_Input>";

            //
            //                inputXml.append("<?xml version=\"1.0\"?>");
            //                                                inputXml.append("<WFUploadWorkItem_Input>");
            //                                                inputXml.append("<Option>WFUploadWorkItem</Option>");
            //                                                inputXml.append("<EngineName>" + ConnectDisconnectFlow.engineName + "</EngineName>");
            //                                                inputXml.append("<SessionId>" + sessionID + "</SessionId>");
            //                                                inputXml.append("<ValidationRequired></ValidationRequired>");
            //                                                inputXml.append("<ProcessDefId>" + processDefID + "</ProcessDefId>");
            //                                                inputXml.append("<InitiateAlso>Y</InitiateAlso>");
            //                                                inputXml.append("<InitiateFromActivityId></InitiateFromActivityId>");
            //                                                inputXml.append("<InitiateFromActivityName></InitiateFromActivityName>");
            //                                                inputXml.append("<DataDefName></DataDefName>");
            //                                                inputXml.append("<Fields></Fields>");
            //                                                inputXml.append("<Documents></Documents>");
            //                                                inputXml.append("<Attributes>" + attributes + "</Attributes>");
            //                                                inputXml.append("<GenerateLogFlag></GenerateLogFlag>");
            //                                                inputXml.append("</WFUploadWorkItem_Input>"); 
            //LogProcessing.statusLogs.info("Creating flow server connection..");
            //LogProcessing.xmlLogs.info("INPUT: " + inputXml);
            
            outputXml = DMSCallBroker.execute(inputXml.toString(), serverIP, Short.parseShort("3333"), 0);
            System.out.println("farman outputxml "+outputXml);
            //LogProcessing.xmlLogs.info("OUTPUT: " + outputXml);
            xmlResponse = new DMSXmlResponse(outputXml);
            mainCode = xmlResponse.getVal("MainCode");
            folderIndex = xmlResponse.getVal("FolderIndex");

            if (mainCode.equals("0")) {
                processInstanceID = xmlResponse.getVal("ProcessInstanceId");
                uploadFlag = "I";
                return processInstanceID;
            } else {
//                LogProcessing.statusLogs.info("Error in creating process instance id for barcode: " + FILE_NO);
//                LogProcessing.errorLogs.info("Error in creating process instance id for barcode: " + FILE_NO);
//                LogProcessing.summaryLogs.info("Error in creating process instance id for barcode: " + FILE_NO);
                uploadFlag = "E";
                return null;
            }
        } catch (Exception e) {
//            LogProcessing.statusLogs.info("Error while executing product call for workitem upload.");
  //          LogProcessing.errorLogs.info("Error while executing product call for workitem upload " + e);
    //        LogProcessing.summaryLogs.info("Error while executing product call for workitem upload " + e.getMessage());
            return null;
        } finally {
            if (xmlResponse != null) {
                xmlResponse = null;
            }
        }
    }
 public String completeWI( String PID, String sessionID) throws FileNotFoundException, IOException {

//        String completeStatus = null;
//        DMSXmlResponse xmlResponse = null;
//        StringBuffer inputXml = new StringBuffer();
//        String outputXml = null;
//        String mainCode = null;
//        String folderIndex = null;
////        String oAttributes = prepAttributesCompleteWI(FILE_NO, volumeId, sessionID);
//        System.out.println(Attributes);
////        String documents = uploadAPPDocComplete(sessionID, FILE_NO, volumeId, PID);
////        System.out.println("Doc : " + documents);
////        if (documents.equalsIgnoreCase("0")) {
////            return "0";
////        }
//        try {
//            inputXml.append("<WMGetWorkItem_Input>");
//            inputXml.append("<Option>WMGetWorkItem</Option>");
//            inputXml.append("<EngineName>" + ConnectDisconnectFlow.engineName + "</EngineName>");
//            inputXml.append("<SessionId>" + sessionID + "</SessionId>");
//            inputXml.append("<ProcessInstanceId>" + PID + "</ProcessInstanceId>");
//            inputXml.append("<WorkItemId>1</WorkItemId>");
//            inputXml.append("</WMGetWorkItem_Input>");
//
//            System.out.println("completeWI => inputXml => " + inputXml);
//            if (false) {
//                outputXml = ConnectDisconnectFlow.executeWithEjbClient(inputXml.toString());
//            } else {
//                outputXml = ConnectDisconnectFlow.executeWithCallBroker(inputXml.toString());
//                System.out.println("+++++++++upload workitem++++++++++++" + outputXml);
//            }
//            xmlResponse = new DMSXmlResponse(outputXml);
//            mainCode = xmlResponse.getVal("MainCode");
//            folderIndex = xmlResponse.getVal("FolderIndex");
//            String str1;
//            if (mainCode.equals("0")) {
//                inputXml.delete(0, inputXml.length());
//                inputXml.append("<WMAssignWorkItemAttributes_Input>");
//                inputXml.append("<Option>WMAssignWorkItemAttributes</Option>");
//                inputXml.append("<EngineName>demo</EngineName>");
//                inputXml.append("<SessionId>" + sessionID + "</SessionId>");
//                inputXml.append("<ProcessInstanceId>" + PID + "</ProcessInstanceId>");
//                inputXml.append("<WorkItemId>1</WorkItemId>");
//                inputXml.append("<ActivityId>9</ActivityId>");//activitydef table//1,2,9
//                inputXml.append("<ProcessDefId>" + defid + "</ProcessDefId>");
//                inputXml.append("<LastModifiedTime></LastModifiedTime>");
//                inputXml.append("<ActivityType>10</ActivityType>");
//                inputXml.append("<complete>D</complete>");
//                inputXml.append("<UserDefVarFlag>Y</UserDefVarFlag>");
//                inputXml.append("<Attributes>" + Attributes + "</Attributes>");
//                inputXml.append("</WMAssignWorkItemAttributes_Input>");
//            } else {
//
//                return null;
//            }
//
//            outputXml = ConnectDisconnectFlow.executeWithEjbClient(inputXml.toString());
////            if (false) {
////                outputXml = ConnectDisconnectFlow.executeWithEjbClient(inputXml.toString());
////            } else {
////                outputXml = ConnectDisconnectFlow.executeWithCallBroker(inputXml.toString());
////                System.out.println("+++++++++upload workitem++++++++++++" + outputXml);
////            }
//            xmlResponse = new DMSXmlResponse(outputXml);
//            mainCode = xmlResponse.getVal("MainCode");
//            folderIndex = xmlResponse.getVal("FolderIndex");
//            if (mainCode.equals("0")) {
//
//            }
//
//        } catch (Exception e) {
//
//            return null;
//        } finally {
//            if (xmlResponse != null) {
//                xmlResponse = null;
//            }
//        }
          String inputXml = "";
        String processInstanceID;
        String outputXml;

        String currentdir = System.getProperty("user.dir");
        String finaldir = currentdir + File.separator + "Propertyfile" + File.separator + "propertytax_conf.properties";
        InputStream is = new FileInputStream(finaldir);

        Properties prop = new Properties();

        prop.load(is);

        defid = prop.getProperty("NewassessmentDefID");
        
        DMSXmlResponse xmlResponse = null;

        try {
            inputXml= "<WMAssignWorkItemAttributes_Input>"
                                    + "<Option>WMAssignWorkItemAttributes</Option>"
                                    + "<EngineName>demo</EngineName>"
                                    + "<SessionId>" + sessionID + "</SessionId>"
                                    + "<ProcessInstanceId>" + PID + "</ProcessInstanceId>"
                                    + "<WorkItemId>1</WorkItemId>"
                                    + "<ActivityId>4</ActivityId>"
                                    + "<ProcessDefId>49</ProcessDefId>"
                                    + "<LastModifiedTime></LastModifiedTime>"
                                    + "<ActivityType>10</ActivityType>"
                                    + "<complete>D</complete>"
                                    + "<UserDefVarFlag>Y</UserDefVarFlag>"
                                    + "<Documents></Documents>"
                                    + "<Attributes></Attributes>"
                                    + "</WMAssignWorkItemAttributes_Input>";
//@            outputXml = ConnectDisconnectFlow.executeWithEjbClient(inputXml.toString());
//            if (false) {
//                outputXml = ConnectDisconnectFlow.executeWithEjbClient(inputXml.toString());
//            } else {
//                outputXml = ConnectDisconnectFlow.executeWithCallBroker(inputXml.toString());
//                System.out.println("+++++++++upload workitem++++++++++++" + outputXml);
//            }

            //LogProcessing.xmlLogs.info("OUTPUT: " + outputXml);
//@            xmlResponse = new DMSXmlResponse(outputXml);
            String mainCode = xmlResponse.getVal("MainCode");
            String folderIndex = xmlResponse.getVal("FolderIndex");

            if (mainCode.equals("0")) {
                processInstanceID = xmlResponse.getVal("ProcessInstanceId");
//                ConfProperty.processID = processInstanceID;
//                uploadFlag = "I";
                //==============Ajit--19022018--InsertQuery================//
                System.out.println("Cremation Inserting new PID/InwardNumber/InwardDate");

           //     String query = "";
//                query = "insert into ng_inout(pid,inward,inwarddate,outward,outwarddate,processname,status) values('"+processInstanceID+"','"+ede.getFile_no()+"','"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"','','','EDE','');";
//                System.out.println("EDE Inserting in ng_inout at File Creating query "+query);
//                method.executeUpdate(ConfProperty.newgenConn.createStatement(), query);
                //==============Ajit--19022018--InsertQuery================//
                System.out.println("ProcessID " + processInstanceID);

                return processInstanceID;
            }
        } catch (Exception ex) {
            return "";
        }



        return "";


    }

    public String outputJSON(String ProcessID) {
        JSONObject json = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            System.out.println("INSIDE OUTPUT JSON");
            json.put("pid", ProcessID);
            json.put("status", "Success");
            json.put("remarks", "WI created Successfully");

        } catch (Exception e) {
            System.out.println(e);

        }
        return json.toString();
    }

    
}
