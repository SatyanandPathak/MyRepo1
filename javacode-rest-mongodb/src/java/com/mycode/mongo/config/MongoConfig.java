package com.mycode.mongo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.mydata.mongo.repository.RepositoryPackage;
import com.mydata.mongo.template.TemplatePackage;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;


@Configuration
@EnableMongoRepositories(basePackageClasses=RepositoryPackage.class)
@ComponentScan(basePackageClasses=TemplatePackage.class)
public class MongoConfig extends AbstractMongoConfiguration {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
    
	
    @Override
    protected String getDatabaseName() {
    	
    	return "test";
    	 
    
    }

    /*@Override
    @Bean
    public MongoClient mongo() throws Exception {
        MongoClient client = new MongoClient(Arrays.asList(
        		   new ServerAddress("LPDCLDUT00341.phx.aexp.com", 27017),
        		   new ServerAddress("LPDCLDUT00342.phx.aexp.com", 27017),
        		   new ServerAddress("LPDCLDUT00343.phx.aexp.com", 27017)));
        

        
        MongoClientOptions.Builder  mcoBuilder = new MongoClientOptions.Builder() ;
        mcoBuilder = mcoBuilder.maxConnectionIdleTime(60000);
        mcoBuilder = mcoBuilder.connectionsPerHost(20);
        LOGGER.info("creating DB connection");

        MongoClient client = new MongoClient(Arrays.asList(
     		   new ServerAddress("LPDCLDUT00341.phx.aexp.com", 27017)), mcoBuilder.build());
        
        client.setWriteConcern(WriteConcern.SAFE);
        return client;
    }
*/
    
    @Override
    @Bean
    public MongoClient mongo() throws Exception {
    	List<ServerAddress> seeds = new ArrayList<ServerAddress>();
	seeds.add(new ServerAddress("localhost", 27017));
        MongoClientOptions.Builder  mcoBuilder = new MongoClientOptions.Builder() ;
        mcoBuilder = mcoBuilder.maxConnectionIdleTime(60000);
        mcoBuilder = mcoBuilder.connectionsPerHost(20);    
			
        MongoClient client = new MongoClient(seeds);
        return client;
    }
    
    
    
    @Override
    protected String getMappingBasePackage() {
        return "com.mydata.mongo.domain";
    }
    
    // ---------------------------------------------------- MongoTemplate
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
    	
    	UserCredentials userCredentials = new UserCredentials("", "");
 	
    	
        final MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName(), userCredentials);
        mongoTemplate.setWriteConcern(WriteConcern.SAFE);
        return mongoTemplate;
    }
    
    // Need this if we want to save any files into 
    
    /*@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
    	return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());   	
    	
    }*/
    
    
}