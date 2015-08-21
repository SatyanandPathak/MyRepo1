/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycode.processors;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mydata.mongo.domain.UserCredentials;
import java.net.UnknownHostException;
import org.json.JSONObject;

/**
 *
 * @author spatha11
 */
public class RegistrationProcessor {
    
    
    public String register (UserCredentials user){
        
        
        JSONObject js = new JSONObject();
     
     MongoClient mongoClient = null;
     try {
         mongoClient = new MongoClient( "localhost" , 27017 );
     } catch (UnknownHostException ex) {
         ex.printStackTrace();
     }
     DB db = mongoClient.getDB( "test" );
     

     
     //DBCollection userCredentials = db.createCollection("UserCredentials", doc); 
     DBCollection userColl = db.getCollection("UserCredentials"); 
     
     DBObject queryForUserName = BasicDBObjectBuilder.start().add("emailId", user.getUserId()).get();
     
      DBCursor cursorUserName = userColl.find(queryForUserName);
     
     
     
     DBObject queryForEmailId = BasicDBObjectBuilder.start().add("emailId", user.getEmailId()).get();
     
      DBCursor cursorEmail = userColl.find(queryForEmailId);
      System.out.println("sursor length::"+cursorEmail.length());
      
      
      if(cursorUserName.length() != 0){
          js.put("status","FAILED");
          js.put("message","UserName already Exists. Please choose a different user name");
      } else if(cursorEmail.length() != 0) {
          js.put("status","FAILED");
          js.put("message","Email ID already Exists. Pleaase enter different email ID");
      } else {
          BasicDBObject doc = new BasicDBObject().append("userName", user.getUserId()). append("password", user.getPassword()).
            append("emailId", user.getEmailId()).append("firstName", user.getFirstName()).append("lastName", user.getLastName());
         userColl.insert(doc);
         js.put("status","SUCCESS");
         js.put("message","Registered successfully");
         System.out.println("Successfully registered");
      }
            
        return js.toString();
    }
    
}
