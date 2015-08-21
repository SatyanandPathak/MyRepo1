package com.mycode.mongo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConnectionManager {
	
	private static ConnectionManager  mongoConfigConnectionManager;
	private static ConnectionManager  appConfigConnectionManager;
	private static AnnotationConfigApplicationContext ctx;
	//private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
	
	private ConnectionManager(){
		
	}
	
	public static ConnectionManager getInstance(){		
		
	
		//LOGGER.info("getInstance:Method entry");
		if(mongoConfigConnectionManager == null ){
			//System.out.println("inside create new connection");
			mongoConfigConnectionManager = new ConnectionManager();
			init(null);
		} 		
		
		return mongoConfigConnectionManager;
		
	}
	
	
	public static ConnectionManager getInstance(Class<?> clazz){		
		
		//System.out.println("inside getInstance");
		//LOGGER.info("getInstance(Class):Method entry");
		if(appConfigConnectionManager == null ){
			//System.out.println("inside create new manager instance");
			appConfigConnectionManager = new ConnectionManager();
			init(clazz);
		}		
		
		return appConfigConnectionManager;
		
	}
	
	

	private static void init(Class<?> clazz) {
		
		//LOGGER.info("inti:Method entry");
		if(clazz == null) {
		  //System.out.println("inside init");
		  ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		} else if(clazz.getName().equals("com.americanexpress.mongo.config.AppConfig")) {
			//System.out.println("creating context for appconfig");
			ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		}
	}
	
	public AnnotationConfigApplicationContext getContext(){
		return ctx;
	}

}
