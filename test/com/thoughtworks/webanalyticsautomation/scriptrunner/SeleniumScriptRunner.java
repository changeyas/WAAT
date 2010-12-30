package com.thoughtworks.webanalyticsautomation.scriptrunner;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.Logger;

public class SeleniumScriptRunner implements IScriptRunner {
    private Logger logger = Logger.getLogger(getClass());
    private Selenium selenium;
    private String CLOSE_DEBUGGER = "//input[@name='close2']";

    public SeleniumScriptRunner(Selenium selenium) {
        this.selenium = selenium;
    }

    public String getHTMLSourceByExecutingScript(String OMNITURE_DEBUGGER_URL, String OMNITURE_DEBUGGER_WINDOW_TITLE, String OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX) {
        try {
            logger.info ("Running script: " + OMNITURE_DEBUGGER_URL);
            selenium.runScript(OMNITURE_DEBUGGER_URL);

            Thread.sleep(5000);
            logger.debug("*** Debugger window title: " + selenium.getTitle());
            selenium.selectWindow(OMNITURE_DEBUGGER_WINDOW_TITLE);
            selenium.click(OMNITURE_DEBUGGER_URL_DECODE_CHECKBOX);
            logger.debug("*** SeleniumScriptDebugger \n HTML source\n" + selenium.getHtmlSource());

        } catch(Exception e)
        {
            logger.info ("NOT able to open " + OMNITURE_DEBUGGER_WINDOW_TITLE);
        }
        closeDebugger();
        return selenium.getHtmlSource();
    }

    private void closeDebugger() {
        logger.info ("Close Omniture Debugger.");
        selenium.click(CLOSE_DEBUGGER);
        logger.info ("Select parent window.");
        selenium.selectWindow("");
    }
}

