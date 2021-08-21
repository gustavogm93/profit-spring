package dev.abel.springbootdocker.generics;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


public class MongoOperations<T extends Property> {

	
	private MongoTemplate mongoTemplate;
	
	public void update(String code, T object){

		//SimpleDateFormat sf = new SimpleDateFormat( OperationManagerConstants.DATE_FORMAT );

	        Query query = new Query();
	        query.addCriteria(Criteria.where("code").is(object.getCode()));	        
	        

	}
	
}
