/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulerpackage;

/**
 *
 * @author Saurish
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public final class LogProcessing {

    public static Logger logInit = null, logConn = null, logAttributes = null,
            newgencalls = null, logErrors = null, logSumm = null, jsonlogs = null;

    public static void settingLogFiles() {
        InputStream is = null;
        try {
            String currentdir = System.getProperty("user.dir");
            String filePath = currentdir + File.separator + "property" + File.separator + "log4j.properties";
            System.out.println(filePath);
            is = new BufferedInputStream(new FileInputStream(filePath));
            Properties ps = new Properties();
            ps.load(is);
            is.close();

            //proper shutdown all nested loggers if already exists
            org.apache.log4j.LogManager.shutdown();

            //configure log property
            PropertyConfigurator.configure(ps);

            logInit = Logger.getLogger("Initialization");
            logConn = Logger.getLogger("Connection");
            logAttributes = Logger.getLogger("Attributes");
            newgencalls = Logger.getLogger("newgencalls");
            logErrors = Logger.getLogger("Errors");
            logSumm = Logger.getLogger("Summary");
                jsonlogs = Logger.getLogger("JSON");
        } catch (Exception e) {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException te) {
                System.out.println("LogProcessing=>settingLogFiles()===Exception===>" + te);
                logErrors.info("Error in setting Logger===>\n" + te);
            }
        }
    }
}
