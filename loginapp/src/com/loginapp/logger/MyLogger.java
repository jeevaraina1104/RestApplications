package com.loginapp.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class MyLogger {

	
	public static Logger getLogger() {
		PropertyConfigurator.configure("C:\\Users\\JE20051838\\eclipse-workspace\\loginapp\\src\\com\\loginapp\\logger\\log4j.properties");
		
		// Get this class related logger object.
		Logger logger = Logger.getLogger(MyLogger.class);
		return logger;
		}
	
	public static void printInfo(String string,String message) {
		getLogger().info(string+"   "+message);
	}
	
	public static void printError(String message) {
		getLogger().error(message);
	}

}
