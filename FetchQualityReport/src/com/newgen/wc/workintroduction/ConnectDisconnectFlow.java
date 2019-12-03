package com.newgen.wc.workintroduction;

//import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.dmsapi.DMSCallBroker;
import com.newgen.dmsapi.DMSInputXml;
import com.newgen.dmsapi.DMSXmlResponse;
import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.wc.conf.ConfProperty;
import com.newgen.wc.conf.LogProcessing;
import java.io.IOException;

public class ConnectDisconnectFlow {

    public static NGEjbClient ngEJBClient;
    public static String engineName = "";
    public static String dmsUserName = "";
    public static String dmsUserPswd = "";
    public static String serverIP = "";
    public static String serverPort = "";
    public static String serverType = "";
    public static String jtsPort = "";
    private static Object objNGOEjbApi;
    static DMSXmlResponse xmlResponse;

    public ConnectDisconnectFlow() throws Exception {
        engineName = ConfProperty.getProperty("EngineName");
        dmsUserName = ConfProperty.getProperty("DMSUserName");
        dmsUserPswd = ConfProperty.getProperty("DMSPassword");
        serverIP = ConfProperty.getProperty("ServerIP");
        serverPort = ConfProperty.getProperty("ServerPort");
        serverType = ConfProperty.getProperty("ServerType");
        jtsPort = ConfProperty.getProperty("JTSPort");
    }

    public String connectFlow() {

        String outputXml = null;

        String sessionId = "";
        String inputXml = "<NGOConnectCabinet_Input>"
                + "    <Option>NGOConnectCabinet</Option>"
                + "    <CabinetName>" + engineName + "</CabinetName>"
                + "    <UserName>" + dmsUserName + "</UserName>"
                + "    <UserPassword>" + dmsUserPswd + "</UserPassword>"
                + "    <Scope>ADMIN</Scope>"
                + "    <UserExist>N</UserExist>"
                + "    <CurrentDateTime></CurrentDateTime>"
                + "    <UserType>U</UserType>"
                + "    <MainGroupIndex>0</MainGroupIndex>"
                + "    <Locale></Locale>"
                + "</NGOConnectCabinet_Input>";
        System.out.println("input xml farman::: " + inputXml);
       
        try {
            String connectCabinetOutpXml = "";
            connectCabinetOutpXml = DMSCallBroker.execute(inputXml, serverIP, Short.parseShort("3333"), 0);
            //    String connectCabinetOutpXml = objNGOEjbApi.ngoExecuteCall(inputXml);
            //  objNGOEjbApi.ngoExecuteCall(inXML.toString());
            System.out.println("Output farman: " + connectCabinetOutpXml);

            xmlResponse = new DMSXmlResponse(connectCabinetOutpXml);

            sessionId = xmlResponse.getVal("UserDBId");
            System.out.println("Session ID " + sessionId);
        } catch (Exception e) {
//            LogProcessing.statusLogs.info("Error while executing product connection call.");
            //           LogProcessing.errorLogs.info("Error while executing product connection call. " + e);
            return null;
        } finally {
            if (xmlResponse != null) {
                xmlResponse = null;
            }
        }
        return sessionId;
    }

    public boolean disconnectFlow(String sessionID) {
        boolean flag = false;
        DMSInputXml xml = new DMSInputXml();
        String outputXml = null;
        DMSXmlResponse xmlResponse = null;

        String inputXml = xml.getDisconnectCabinetXml(engineName, sessionID);

        try {
            LogProcessing.statusLogs.info("Disconnecting flow server..");
            //LogProcessing.xmlLogs.info("INPUT: " + inputXml);

            if (ConfProperty.getProperty("IsNGejbAllow").equalsIgnoreCase("Y")) {
                outputXml = executeWithEjbClient(inputXml);
            } else {
                outputXml = executeWithCallBroker(inputXml);
            }

            //LogProcessing.xmlLogs.info("OUTPUT: " + outputXml);
            xmlResponse = new DMSXmlResponse(outputXml);
            if (xmlResponse.getVal("Status").equalsIgnoreCase("0")) {
                flag = true;
            } else {
                LogProcessing.statusLogs.info("Error in disconnecting flow server. Session: " + sessionID);
                LogProcessing.errorLogs.info("Error in disconnecting flow server. Session: " + sessionID);
                flag = false;
            }

        } catch (Exception e) {
            LogProcessing.statusLogs.info("Error while executing product call.");
            LogProcessing.errorLogs.info("Error while executing product call. " + e);
            return false;
        } finally {
            if (xmlResponse != null) {
                xmlResponse = null;
            }
            if (xml != null) {
                xml = null;
            }
        }
        return flag;
    }

    public static String executeWithCallBroker(String inputXml) throws NGException, IOException {

        String outputXml = "";
        // LogProcessing.xmlLogs.info("INPUT: " + inputXml);
        System.out.println("-1 first executeWithCallBroker");
        System.out.println("inputXml **** " + inputXml);
        System.out.println("serverIP **** " + serverIP);
        System.out.println("Short.parseShort(jtsPort) **** " + Short.parseShort(jtsPort));

        // outputXml = DMSCallBroker.execute(inputXml, serverIP, Short.parseShort(jtsPort), 0);
        //outputXml = objNGOEjbApi.ngoExecuteCall(inputXml, serverIP, Short.parseShort(jtsPort), 0);
        // LogProcessing.xmlLogs.info("OUTPUT: " + outputXml);
        return outputXml;
    }

    public static String executeWithEjbClient(String inputXml) throws NGException {
        String outputXml = "";
        // LogProcessing.xmlLogs.info("INPUT: " + inputXml);
        ngEJBClient = NGEjbClient.getSharedInstance();

        System.out.println("-2 second executeWithEjbClient");
        System.out.println("serverIP **** " + serverIP);
        System.out.println("serverPort **** " + serverPort);
        System.out.println("serverType **** " + serverType);
        System.out.println("inputXml **** " + inputXml);
        outputXml = ngEJBClient.makeCall(serverIP, serverPort, serverType, inputXml);
        // LogProcessing.xmlLogs.info("OUTPUT: " + outputXml);
        return outputXml;
    }
}
