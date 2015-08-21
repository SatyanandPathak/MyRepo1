package com.mydata.mongo.repository;

import com.mydata.mongo.domain.UserCredentials;
import java.util.List;

import org.springframework.data.repository.CrudRepository;



public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long>{
	
    public UserCredentials findByUserId(String userId);
    
    public UserCredentials findByUserIdAndPassword(String userId, String password);	
	

}
