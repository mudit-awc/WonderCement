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


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saurish
 */
public class Schedulermain {

    /**
     * @param args the command line arguments
     */
    public static void main(String ar[]) {
        try {
            LogProcessing.settingLogFiles();
            Schedulerprocessing s = new Schedulerprocessing();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("------------- Web Service Utility Start --------------");

                    try {
                        s.beginProcessing();
                    } catch (Exception ex) {
                    	  LogProcessing.logErrors.info("Exception Occured :: " + ex);
                    } 

                    System.out.println("------------- Waiting for Processing --------------");
                }
            }, 0, 12000);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}

