package com.wcl.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.wcl.workitemprocessing.StartWorkitemProcessing;

public class InitiateProcessing {

	public static Properties WCLProperty = new Properties();
	static String propertyFileDir = System.getProperty("user.dir") + File.separator + "conf" + File.separator
			+ "conf.properties";
	static String loggerFileDir = System.getProperty("user.dir") + File.separator + "conf" + File.separator
			+ "WCLLog4j.properties";

	static {
		InputStream IS_propertyFileDir;
		try {
			IS_propertyFileDir = new FileInputStream(propertyFileDir);
			WCLProperty.load(IS_propertyFileDir);
		} catch (IOException IOE) {
			System.out.println("##Error in loading property file : ");
			IOE.printStackTrace();
			System.exit(0);
		}

		LoadLogs.settingLogFiles(loggerFileDir);
		LoadConnections.createDBConnection();
	}

	public static void main(String[] args) {
		try {

			new StartWorkitemProcessing().completePendingWI();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
