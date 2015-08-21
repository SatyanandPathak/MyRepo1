
package com.mycode.springservice.controller;

/**
 *
 * @author spatha11
 */

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mycode.mongo.config.ConnectionManager;
import com.mycode.processors.RegistrationProcessor;
import com.mydata.mongo.domain.UserCredentials;
import com.mydata.mongo.repository.UserCredentialsRepository;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth/service")

public class SpringServiceController {
 @RequestMapping(value = { "/authenticateUser" }, method = RequestMethod.POST , consumes = { "application/json" })
 public String authenticateUser(@RequestBody String userRequestJsonObject) {
  
  JSONObject jsonObj = new JSONObject(userRequestJsonObject);
  System.out.println("inside the controller"+jsonObj);
     MongoClient mongoClient = null;
     try {
         mongoClient = new MongoClient( "localhost" , 27017 );
     } catch (UnknownHostException ex) {
         Logger.getLogger(SpringServiceController.class.getName()).log(Level.SEVERE, null, ex);
     }
     
          
  String userName = (String)jsonObj.get("userName");
  String password = (String)jsonObj.get("password");
     DB db = mongoClient.getDB( "test" );
     

     
     //DBCollection userCredentials = db.createCollection("UserCredentials", doc); 
     DBCollection userColl = db.getCollection("UserCredentials"); 
     BasicDBObject query = new BasicDBObject();
     query.append("userName",userName);
     query.append("password",password);
     
     
      DBCursor cursor = userColl.find(query);
      System.out.println("sursor length::"+cursor.length());
  JSONObject js = new JSONObject();
      if(cursor.length() != 0){
          js.put("status","SUCCESS");
          js.put("message", "Credentials matched");
          js.put("userName", userName);
      } else {
         js.put("status","FAILED"); 
         js.put("message", "Please check the User Name/Password");
      }
    
    String response = js.toString();

  
  System.out.println("returning the final value");

  return response;
 }
 
 
 
 
 
 @RequestMapping(value = { "/registerUser" }, method = RequestMethod.POST , consumes = { "application/json" })
 public String registerUser(@RequestBody String registerRequestJSON) {
  
     JSONObject jsonObj = new JSONObject(registerRequestJSON);
     String userName = (String)jsonObj.get("newUserName");
     String password = (String)jsonObj.get("newPassword");
     String rePassword = (String)jsonObj.get("newrePassword");
     String emailId = (String)jsonObj.get("emailId");
     String firstName = (String)jsonObj.get("firstName");
     String lastName = (String)jsonObj.get("firstName"); 
     
     UserCredentials user = new UserCredentials();
     user.setUserId(userName);
     user.setPassword(password);
     user.setEmailId(emailId);
     user.setFirstName(firstName);
     user.setLastName(lastName);
     
     
     RegistrationProcessor registerProcessor = new RegistrationProcessor();
     String response = registerProcessor.register(user);
     
     
     
     return response;
 }
 
 
 
 }
 
 
 
 






