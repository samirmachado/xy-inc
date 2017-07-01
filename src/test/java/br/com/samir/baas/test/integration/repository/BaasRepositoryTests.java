package br.com.samir.baas.test.integration.repository;

import static org.junit.Assert.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import br.com.samir.baas.database.Database;
import br.com.samir.baas.repository.BaasRepository;
import br.com.samir.baas.test.integration.IntegrationTest;

public class BaasRepositoryTests extends IntegrationTest {

	@Autowired
	private MongoClient mongoClient;
	
	@Autowired
	private BaasRepository baasRepository;
	
	@Autowired
	Database database;
	
	@Test
	public void insertTest() {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String insertedJsonObjectId = Document.parse(insertedJsonObject).get("_id").toString();
		Bson bsonObject = new Document().append("_id", new ObjectId(insertedJsonObjectId));
		String foundObject = database.getDatabase().getCollection(tableName).find(bsonObject).first().toJson();
		
		assertEquals(insertedJsonObject, foundObject);
	}

	@Test
	public void findByIdTestWithObjectInDatabase() {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String insertedJsonObjectId = Document.parse(insertedJsonObject).get("_id").toString();
		String foundObject = baasRepository.findById(tableName, insertedJsonObjectId);
		
		assertEquals(insertedJsonObject, foundObject);
	}
	
	@Test
	public void findByIdTestWithNonExistentObjectInDatabase() {
		String tableName = "table";
		String insertedJsonObjectId = new ObjectId().toHexString();

		String foundObject = baasRepository.findById(tableName, insertedJsonObjectId);
		
		assertNull(foundObject);
	}

	@Test
	public void getTest() {
		MongoDatabase database = mongoClient.getDatabase("mydb");

		MongoCollection<Document> collection = database.getCollection("user");

		Document document = new Document("name", "samir");
		collection.insertOne(document);

		document = new Document("name", "joao").append("age", 5);
		collection.insertOne(document);

		collection = database.getCollection("cars");

		document = new Document("name", "vectra");
		collection.insertOne(document);

		document = new Document("name", "corsa").append("fuel", "gasoline");
		collection.insertOne(document);

		collection = database.getCollection("user");

		document = new Document("name", "samir");
		collection.insertOne(document);

		document = new Document("name", "tereza");
		collection.insertOne(document);

		MongoCursor<String> cursor = database.listCollectionNames().iterator();
		while (cursor.hasNext()) {
			String name = cursor.next();
			System.out.println(name);
			collection = database.getCollection(name);
			FindIterable<Document> documents = collection.find();
			for (Document documentItem : documents) {
				System.out.println(documentItem.toJson());
			}
		}

		collection = database.getCollection("user");
		MongoCursor<Document> queryResult = collection.find(com.mongodb.client.model.Filters.eq("name", "samir"))
				.iterator();
		while (queryResult.hasNext()) {
			Document value = queryResult.next();
			System.out.println(value.toJson());
		}
	}
}
