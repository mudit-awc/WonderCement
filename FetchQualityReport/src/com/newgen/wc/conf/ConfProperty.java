/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.wc.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anchal Dua
 */
public class ConfProperty {

    public static Properties prop = new Properties();
    public static HashSet<String> readynewFileno = new HashSet<>();
    public static HashSet<String> readyoldFileno = new HashSet<>();
    public static HashSet<String> totalnewFileno = new HashSet<String>();
    public static HashSet<String> totaloldFileno = new HashSet<String>();
    public static HashSet<String> unSuccessWIUpload = new HashSet<String>();
    public static Connection newgenConn = null;

    static {
        //1
        try {
            FileInputStream fis = new FileInputStream("E:\\Newgen\\Server\\jboss-eap-6.4\\bin\\WonderCement" + File.separator + "conf" + File.separator + "conf.properties");
            prop.load(fis);

            Enumeration e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String val = prop.get(key) != null ? prop.get(key).toString() : null;

                if (val == null || val.trim().equals("")) {
                    System.out.print("Invalid conf file entry for key :" + key);
                    LogProcessing.statusLogs.info("Invalid conf file entry for key :" + key);
                    System.exit(0);
                } else {
                    prop.setProperty(key.trim().toLowerCase(), val.trim());
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(0);
        }

        try {
            //2
            createConnection();
        } catch (InterruptedException ex) {
            Logger.getLogger(ConfProperty.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getProperty(String key) throws Exception {
        String val = null;
        val = prop.getProperty(key.toLowerCase().trim());

        if (val == null) {
            throw new Exception("No value found for Key:" + key.toLowerCase().trim());
        } else {
            return val;
        }
    }

    public static Connection createConnection() throws InterruptedException {
        System.out.println("inside connection (ConfProperty) class");
        Connection NewgentempCon = null;
        String sid = "";
        String dbIP = "";
        String dbDriverClass = "";
        String dbDriverSource = "";
        String username = "";
        String password = "";
        try {
            NewgentempCon = newgenConn;
            //newgen DB details
            sid = ConfProperty.getProperty("DatabaseName");
            System.out.println("sid  --- " + sid);
            dbIP = ConfProperty.getProperty("DatabaseIP");
            System.out.println("dbIP  --- " + dbIP);
            dbDriverClass = ConfProperty.getProperty("DatabaseDriverClass");
            dbDriverSource = ConfProperty.getProperty("DatabaseDriverSource");
            username = ConfProperty.getProperty("UserName");
            password = ConfProperty.getProperty("Password");

            Properties info = new java.util.Properties();
            info.put("user", ConfProperty.getProperty("UserName"));
            info.put("password", ConfProperty.getProperty("Password"));
            info.put("charSet", ConfProperty.getProperty("CharSet"));
            info.put("DatabaseName", sid);

            Class.forName(dbDriverClass);

            //reset newgen connection if exists
            try {
                System.out.println("inside try");
                if (NewgentempCon != null) {
                    NewgentempCon.close();
                    NewgentempCon = null;
                }
            } catch (SQLException se) {
                LogProcessing.printConsoleLog("Exception in reseting Newgen connection  :" + se.getMessage());
                System.exit(1);
            }

            //creating Newgen connection
            do {
                try {
                    System.out.println("inside do while");
                    System.out.println("NewgentempCon " + NewgentempCon);
                    System.out.println("dbDriverSource " + dbDriverSource);
                    System.out.println("username " + username);
                    System.out.println("password " + password);
                    NewgentempCon = DriverManager.getConnection(dbDriverSource, username, password);
                    System.out.println("NewgentempCon " + NewgentempCon);
                    System.out.println("Connected Newgen DB");

                } catch (SQLException sqle) {
                    System.out.println("Error in connecting newgen database. " + sqle.getMessage());
                    NewgentempCon = null;
                }

                if (NewgentempCon == null) {
                    System.out.println("Waiting for newgen connection...");
                    Thread.sleep(5000);
                } else {
                    // LogProcessing.statusLogs.debug("Re-connected with newgen database. ");
                    System.out.println("Re-connected with newgen database.");
                }
            } while (NewgentempCon == null);
            newgenConn = NewgentempCon;
            System.out.println("newgenConn " + newgenConn);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            //  LogProcessing.errorLogs.info("Error While making connection with database.", cnfe);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            //   LogProcessing.errorLogs.info("Error While making connection with database.", e);
            System.exit(0);
        }
        return NewgentempCon;
    }
}
