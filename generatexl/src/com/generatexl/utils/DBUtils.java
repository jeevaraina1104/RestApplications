package com.generatexl.utils;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class DBUtils {
	
	
	public static DB connection() {
		
		MongoClient Db=new MongoClient();
		DB db=Db.getDB("mydb");  
		return db ;
	}

}
