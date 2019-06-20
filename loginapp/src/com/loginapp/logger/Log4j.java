package com.loginapp.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.loginapp.main.AppMain;

public class Log4j {
	
	public static Logger getLogger() {
	PropertyConfigurator.configure("C:\\Users\\JE20051838\\eclipse-workspace\\loginapp\\src\\com\\loginapp\\logger\\log4j.properties");
	 
	Logger rootLogger = Logger.getRootLogger();
	
	// Get this class related logger object.
	Logger logger = Logger.getLogger(AppMain.class);
	return logger;
	}

}
