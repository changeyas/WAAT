package com.thoughtworks.webanalyticsautomation.runUtils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 1:07:03 PM
 */

public class UIDriverThreadRunner implements Runnable {
    private String command = null;
    private Logger logger = null;
    private Process process;

    public UIDriverThreadRunner(Logger logger) {
        this.logger = logger;
    }

    public void runInThread (String command) {
        this.command = command;
        Thread myThread = new Thread(this);
        myThread.start();
    }

    public void run() {
        logger.info("Running command in a new thread: " + command);
        try {
            logger.debug("*** Current dir: " + System.getProperty("user.dir"));
            process = Runtime.getRuntime().exec(command);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                logger.debug(line);
            }

            InputStream ies = process.getErrorStream();
            InputStreamReader iesr = new InputStreamReader(ies);
            BufferedReader ebr = new BufferedReader(iesr);
            String errorLine;

            while ((errorLine = ebr.readLine()) != null) {
                logger.debug (errorLine);
            }
            ebr.close();
            iesr.close();
            ies.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        logger.info ("Calling stop in the thread");
        process.destroy();
    }
}