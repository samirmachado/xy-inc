package br.com.samir.baas.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Repository
public class Database {
	
	@Autowired 
	private MongoClient mongoClient;
	
	public MongoDatabase getDatabase() {
		return mongoClient.getDatabase("database");
	}
}
