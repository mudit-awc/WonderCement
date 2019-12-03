/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.wc.conf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
//import com.newgen.wc.conf.methods.Methods;


/**
 *
 * @author Anchal Dua
 */
public class LogProcessing {

    public static Logger errorLogs = null;
    public static Logger statusLogs = null;
    public static Logger xmlLogs = null;
    public static Logger validationErrorLogs = null;
    public static Logger validationStatusLogs = null;

    public static Logger summaryLogs = null;

    public static void settingLogFiles() {
        InputStream is = null;
        try {
            String filePath = "E:\\Newgen\\Server\\jboss-eap-6.4\\bin\\WonderCement"+ File.separatorChar + "conf" + File.separatorChar + "log4j.properties";
            System.out.println(filePath + "File path");
                is = new BufferedInputStream(new FileInputStream(filePath));
            Properties ps = new Properties();
            ps.load(is);
            is.close();

            //proper shutdown all nested loggers if already exists
            org.apache.log4j.LogManager.shutdown();

            //configure log property
            PropertyConfigurator.configure(ps);

            errorLogs = Logger.getLogger("BulkUploadError");
            statusLogs = Logger.getLogger("BulkUploadStatus");
            xmlLogs = Logger.getLogger("BulkUploadXml");
            validationErrorLogs = Logger.getLogger("ValidationError");
            validationStatusLogs = Logger.getLogger("ValidationStatus");
            summaryLogs = Logger.getLogger("Summary");
            dumpInitialLogs();

        } catch (Exception e) {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException te) {
                errorLogs.info("Error in setting Logger : " + te);
            }
            e.printStackTrace();
        }
    }

    public static void dumpInitialLogs() {
        errorLogs.info("==============================================");
        errorLogs.info("Error Log Initialized for document upload");
        errorLogs.info("==============================================");
        statusLogs.info("=============================================");
        statusLogs.info("Status Log Initialized for document upload");
        statusLogs.info("==============================================");
        xmlLogs.info("=============================================");
        xmlLogs.info("XML Log Initialized for document upload");
        xmlLogs.info("==============================================");
        validationErrorLogs.info("==============================================");
        validationErrorLogs.info("Error Log Initialized for document upload");
        validationErrorLogs.info("==============================================");
        validationStatusLogs.info("=============================================");
        validationStatusLogs.info("Status Log Initialized for document upload");
        validationStatusLogs.info("==============================================");
        summaryLogs.info("=============================================");
        summaryLogs.info("Summary Log Initialized for document upload");
        summaryLogs.info("==============================================");
    }

    public static void dumpFinalLogs() {
            errorLogs.info("==============================================");
        errorLogs.info("Error Log Ends for document upload");
        errorLogs.info("==============================================");
        statusLogs.info("=============================================");
        statusLogs.info("Status Log Ends for document upload");
        statusLogs.info("==============================================");
        xmlLogs.info("=============================================");
        xmlLogs.info("XML Log Ends for document upload");
        xmlLogs.info("==============================================");
        validationErrorLogs.info("==============================================");
        validationErrorLogs.info("Error Log Ends for document upload");
        validationErrorLogs.info("==============================================");
        validationStatusLogs.info("=============================================");
        validationStatusLogs.info("Status Log Ends for document upload");
        validationStatusLogs.info("==============================================");
        summaryLogs.info("=============================================");
        summaryLogs.info("Summary Log Ends for document upload");
        summaryLogs.info("==============================================");
    }

    public static void printConsoleLog(String msg) {
        LogProcessing.statusLogs.info(msg);
//        System.out.println(new Methods().getDate() + " : " + msg);
    }

}
