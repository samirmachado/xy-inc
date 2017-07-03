package br.com.samir.baas.test.integration.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.samir.baas.database.Database;
import br.com.samir.baas.exception.InvalidJsonObjectException;
import br.com.samir.baas.exception.NotFoundException;
import br.com.samir.baas.repository.BaasRepository;
import br.com.samir.baas.test.integration.IntegrationTest;

public class BaasRepositoryTests extends IntegrationTest {

	@Autowired
	private BaasRepository baasRepository;
	
	@Autowired
	Database database;
	
	@Before
	public void testSetup() {
		clearDatabase();
	}
	
	@Test
	public void insertTest() throws InvalidJsonObjectException {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String insertedJsonObjectId = Document.parse(insertedJsonObject).get("_id").toString();
		Bson bsonObject = new Document().append("_id", new ObjectId(insertedJsonObjectId));
		String foundObject = database.getDatabase().getCollection(tableName).find(bsonObject).first().toJson();
		
		assertEquals(insertedJsonObject, foundObject);
	}

	@Test
	public void findByIdTestWithObjectInDatabase() throws InvalidJsonObjectException, NotFoundException {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String insertedJsonObjectId = Document.parse(insertedJsonObject).get("_id").toString();
		String foundObject = baasRepository.findById(tableName, insertedJsonObjectId);
		
		assertEquals(insertedJsonObject, foundObject);
	}
	
	@Test
	public void findByIdTestWithNonExistentObjectInDatabase() throws NotFoundException {
		String tableName = "table";
		String jsonObjectId = new ObjectId().toHexString();

		String foundObject = baasRepository.findById(tableName, jsonObjectId);
		
		assertNull(foundObject);
	}
	
	@Test
	public void removeTestWithObjectInDatabase() throws InvalidJsonObjectException, NotFoundException {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String insertedJsonObjectId = Document.parse(insertedJsonObject).get("_id").toString();
		baasRepository.remove(tableName, insertedJsonObjectId);
		String foundObject = baasRepository.findById(tableName, insertedJsonObjectId);
		
		assertNull(foundObject);
	}
	
	@Test(expected = NotFoundException.class)
	public void removeTestWithNonExistentObjectInDatabase() throws NotFoundException {
		String tableName = "table";
		String jsonObjectId = new ObjectId().toHexString();

		baasRepository.remove(tableName, jsonObjectId);
	}
	
	@Test
	public void updateTestWithObjectInDatabase() throws InvalidJsonObjectException, NotFoundException {
		String tableName = "table";
		String originalValueName = "João";
		String updatedValueName = "Maria";
		Integer age = 18;
		String jsonObject = new Document().append("name", originalValueName).append("age", age).toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String insertedJsonObjectId = Document.parse(insertedJsonObject).get("_id").toString();
		
		String originalObjectInDatabase = baasRepository.findById(tableName, insertedJsonObjectId);
		String objectToUpdate = Document.parse(originalObjectInDatabase).append("name", updatedValueName).toJson();
		
		baasRepository.update(tableName, objectToUpdate, insertedJsonObjectId);
		String updatedObjectInDatabase = baasRepository.findById(tableName, insertedJsonObjectId);
		
		assertEquals(originalValueName, Document.parse(originalObjectInDatabase).get("name"));
		assertEquals(age, Document.parse(originalObjectInDatabase).get("age"));
		assertEquals(updatedValueName, Document.parse(updatedObjectInDatabase).get("name"));
		assertEquals(age, Document.parse(updatedObjectInDatabase).get("age"));
	}
	
	@Test(expected = NotFoundException.class)
	public void upateTestWithNonExistentObjectInDatabase() throws NotFoundException, InvalidJsonObjectException {
		String tableName = "table";
		String jsonObjectId = new ObjectId().toHexString();
		String objectToUpdate = new Document().append("_id", jsonObjectId).append("name", "joão").toJson();
		
		baasRepository.update(tableName, objectToUpdate, jsonObjectId);
	}
	
	@Test
	public void listTest() throws InvalidJsonObjectException {
		String tableName = "table";
		String jsonObjectOne = new Document().append("name", "joão").toJson();
		String jsonObjectTwo = new Document().append("name", "Tereza").toJson();
		
		baasRepository.insert(tableName, jsonObjectOne);
		baasRepository.insert(tableName, jsonObjectTwo);
		List<String> objectsInDatabase = baasRepository.list(tableName);
		
		assertEquals(2, objectsInDatabase.size());
		objectsInDatabase.forEach(item -> {
			String name = Document.parse(item).getString("name");
			assertTrue(name.equals("joão") || name.equals("Tereza"));
		});
	}
	
	@Test
	public void listTestWithEmptyCollection() {
		String tableName = "table";
		
		List<String> objectsInDatabase = baasRepository.list(tableName);
		
		assertEquals(0, objectsInDatabase.size());
	}
}
