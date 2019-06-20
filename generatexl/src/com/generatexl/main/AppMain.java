package com.generatexl.main;

import com.wipro.c4.core.utils.infra.Config;
import com.wipro.c4.core.utils.infra.ConfigUtility;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.generatexl.service.GenerateService;
import com.generatexl.service.GenerateService1;

public class AppMain extends Application<Config> {

	public static void main(String[] args) throws Exception {
		System.out.println("r");
		new AppMain().run(args);
	}

	@Override
	public String getName() {
		return "dictionaryapi-service";
	}

	@Override
	public void initialize(Bootstrap<Config> bootstrap) {
	}
	
	
	@Override
	public void run(Config configuration, Environment environment) {
		Config.setConfig(configuration);
		ConfigUtility.configureCors(environment, Config.getConfig().getcORSConfig().getSupportedMethods(),
				Config.getConfig().getcORSConfig().getAllowedHeaders());	
		environment.jersey().register(GenerateService1.class);
	}
	

}
