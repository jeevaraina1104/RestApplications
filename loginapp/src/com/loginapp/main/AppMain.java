package com.loginapp.main;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.loginapp.client.services.LoginAppService;
import com.loginapp.logger.Log4j;
import com.wipro.c4.core.utils.infra.Config;
import com.wipro.c4.core.utils.infra.ConfigUtility;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AppMain extends Application<Config> {

	public static void main(String[] args) throws Exception {
		Log4j.getLogger().info("APP main method is called");
		System.out.println("r");
		
		new AppMain().run(args);
	}

	@Override
	public String getName() {
		return "Loginapp-service";
	}

	@Override
	public void initialize(Bootstrap<Config> bootstrap) {
	}
	
	
	@Override
	public void run(Config configuration, Environment environment) {
		Config.setConfig(configuration);
		ConfigUtility.configureCors(environment, Config.getConfig().getcORSConfig().getSupportedMethods(),
				Config.getConfig().getcORSConfig().getAllowedHeaders());	
		environment.jersey().register(LoginAppService.class);
	}
	

}