/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulerpackage;

import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.dmsapi.DMSXmlResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import static schedulerpackage.GetSetGateEntryData.xmlResponse;

/**
 *
 * @author Saurish
 */
public class Schedulerprocessing {
    
    String Query = null, Query1 = null, Query2 = null, listviewxml = null, inputxml = null, outputxml = null, sessionid = null, attri = null, processid = null;
    ResultSet rs = null, rs1 = null;
    String purchaseorderno = null, invoiceno = null;
    static DMSXmlResponse xmlResponse;
    
    public void beginProcessing() throws FileNotFoundException, IOException {
        String currentdir = System.getProperty("user.dir");
        String finaldir = currentdir + File.separator + "property" + File.separator + "conf.properties";
        InputStream is = new FileInputStream(finaldir);
        
        Properties prop = new Properties();
        
        prop.load(is);
        String ProcessDefId = "", activityName = "", activityID = "", DBUsername = "", DBPassowrd = "", CabinetName = "", DMSUserName = "", DMSUserPassword = "", serverType = "", serverIP = "", ServerPort = "", DatabaseDriverSource = "", DatabaseDriverClass = "";
        int serverport1 = 0;
        activityName = prop.getProperty("activityName");
        activityID = prop.getProperty("activityID");
        ProcessDefId = prop.getProperty("ProcessDefID");
        DBUsername = prop.getProperty("UserName");
        DBPassowrd = prop.getProperty("Password");
        CabinetName = prop.getProperty("EngineName");
        DMSUserName = prop.getProperty("DMSUserName");
        DMSUserPassword = prop.getProperty("DMSPassword");
        serverType = prop.getProperty("ServerType");
        serverIP = prop.getProperty("ServerIP");
        ServerPort = prop.getProperty("ServerPort");
        DatabaseDriverSource = prop.getProperty("DatabaseDriverSource");
        DatabaseDriverClass = prop.getProperty("DatabaseDriverClass");
        int serverport11 = Integer.parseInt(ServerPort);
        String GateLineContractXML = "";
        
        inputxml = "<NGOConnectCabinet_Input>"
                + "    <Option>NGOConnectCabinet</Option>"
                + "    <CabinetName>" + CabinetName + "</CabinetName>"
                + "    <UserName>" + DMSUserName + "</UserName>"
                + "    <UserPassword>" + DMSUserPassword + "</UserPassword>"
                + "    <Scope>ADMIN</Scope>"
                + "    <UserExist>N</UserExist>"
                + "    <CurrentDateTime></CurrentDateTime>"
                + "    <UserType>U</UserType>"
                + "    <MainGroupIndex>0</MainGroupIndex>"
                + "    <Locale></Locale>"
                + "</NGOConnectCabinet_Input>";
        LogProcessing.newgencalls.info("Connect Input XML :: " + inputxml);
        outputxml = DMSCallBroker.execute(inputxml, serverIP, Short.parseShort("3333"), 0);
        LogProcessing.newgencalls.info("Connect Output XML :: " + outputxml);
        xmlResponse = new DMSXmlResponse(outputxml);
        
        sessionid = xmlResponse.getVal("UserDBId");
        LogProcessing.logSumm.info("Session ID ::::: " + sessionid);

        //Set Header Details
        try {
            Class.forName(DatabaseDriverClass);
            Connection con = DriverManager.getConnection(
                    DatabaseDriverSource, DBUsername, DBPassowrd);
            Statement stmt = con.createStatement();
            
            Query = "select processinstanceid from wfinstrumenttable where processdefid=" + ProcessDefId + " and activityname='" + activityName + "'";
            LogProcessing.logSumm.info("Query to Find WorkItem :: " + Query);
            rs = stmt.executeQuery(Query);
            
            while (rs.next()) {
                Query1 = "select purchaseorderno,invoiceno from ext_supplypoinvoices where processid='" + rs.getString(1) + "'";
                rs1 = stmt.executeQuery(Query1);
                while (rs1.next()) {
                    purchaseorderno = rs1.getString(1);
                    invoiceno = rs1.getString(2);
                }
                GeneralMethods objGeneral = new GeneralMethods();
                attri = attri + objGeneral.populatePurchaseOrder(purchaseorderno);
                attri = attri + objGeneral.populateGateEntry(purchaseorderno, invoiceno);
                LogProcessing.logAttributes.info("Final Attributes ::: " + attri);
                processid = rs.getString(1);
                inputxml = " <WMGetWorkItem_Input> \n"
                        + " <Option>WMGetWorkItem</Option>\n"
                        + " <EngineName>" + CabinetName + "</EngineName>\n"
                        + " <SessionId>" + sessionid + "</SessionId>\n"
                        + " <ProcessInstanceId>" + processid + "</ProcessInstanceId>\n"
                        + " <WorkItemId>1</WorkItemId> \n"
                        + " </WMGetWorkItem_Input>";
                LogProcessing.newgencalls.info(inputxml);
                outputxml = DMSCallBroker.execute(inputxml, serverIP, Short.parseShort("3333"), 0);
                LogProcessing.newgencalls.info(outputxml);
                xmlResponse = new DMSXmlResponse(outputxml);
                if (xmlResponse.getVal("MainCode").equalsIgnoreCase("0")) {
                    
                    inputxml = "<WMAssignWorkItemAttributes_Input>"
                            + "<Option>WMAssignWorkItemAttributes</Option>"
                            + "<EngineName>" + CabinetName + "</EngineName>"
                            + "<SessionId>" + sessionid + "</SessionId>"
                            + "<ProcessInstanceId>" + processid + "</ProcessInstanceId>"
                            + "<WorkItemId>1</WorkItemId>"
                            + "<ActivityId>" + activityID + "</ActivityId>"
                            + "<ProcessDefId>" + ProcessDefId + "</ProcessDefId>"
                            + "<LastModifiedTime></LastModifiedTime>"
                            + "<ActivityType>10</ActivityType>"
                            + "<complete>D</complete>"
                            + "<UserDefVarFlag>Y</UserDefVarFlag>"
                            + "<Documents></Documents>"
                            + "<Attributes>" + attri + "</Attributes>"
                            + "</WMAssignWorkItemAttributes_Input>";
                    LogProcessing.newgencalls.info("Assign Wali Input ::: " + inputxml);
                    
                    outputxml = DMSCallBroker.execute(inputxml, serverIP, Short.parseShort("3333"), 0);
                    LogProcessing.newgencalls.info(outputxml);
                    
                }
            }
        } catch (Exception ex) {
            LogProcessing.logErrors.info("Exception Occured....!!!! " + ex);
        }
    }
}
