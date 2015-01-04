package com.illucit.partyinvoice;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Logging Helper.
 * 
 * @author Christian Simon
 *
 */
public class LogSetup {

	/**
	 * Setup log4j.
	 * 
	 * @param userSettingsDir
	 *            directory which should contain the logfile
	 */
	public static void setupLogging(File userSettingsDir) {
		File logFile = new File(userSettingsDir, "partyinvoice.log");

		Logger rootLogger = Logger.getRootLogger();
		PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} [%-5p] %m%n");

		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		rootLogger.addAppender(consoleAppender);

		try {
			FileAppender fileAppender = new FileAppender(layout, logFile.getPath(), true);
			rootLogger.addAppender(fileAppender);
		} catch (IOException e) {
			System.err.println("Error opening logfile");
			e.printStackTrace();
		}
	}

}
