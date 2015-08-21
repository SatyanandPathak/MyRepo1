package com.mycode.mongo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({ "com.mycode.mongo.config", "com.mycode.mongo.repository", "com.mycode.mongo.service" })
@Import({ MongoConfig.class })
public class AppConfig {

}