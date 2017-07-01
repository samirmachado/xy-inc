package br.com.samir.baas.test.integration.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.samir.baas.database.Database;
import br.com.samir.baas.repository.BaasRepository;
import br.com.samir.baas.test.integration.IntegrationTest;

public class BaasRepositoryTests extends IntegrationTest {

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
}
