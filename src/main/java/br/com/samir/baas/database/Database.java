package br.com.samir.baas.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Repository
public class Database {
	
	@Value("${database.name}")
	private String databaseName;
	
	@Autowired 
	private MongoClient mongoClient;
	
	public MongoDatabase getDatabase() {
		return mongoClient.getDatabase(databaseName);
	}

	public void clear() {
		getDatabase().drop();
	}
}
