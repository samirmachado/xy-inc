package br.com.samir.baas.test.integration.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.samir.baas.exception.InvalidJsonObjectException;
import br.com.samir.baas.exception.NotFoundException;
import br.com.samir.baas.repository.BaasRepository;
import br.com.samir.baas.test.integration.IntegrationTest;

public class BaasControllerTests extends IntegrationTest {
	
	@Autowired
	private BaasRepository baasRepository;
	
	@Before
	public void testSetup() {
		clearDatabase();
	}

	@Test
	public void findByIdTest() throws InvalidJsonObjectException {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String insertedJsonObjectId = Document.parse(insertedJsonObject).get("_id").toString();
		ResponseEntity<String> response = get("/"+tableName+"/"+insertedJsonObjectId);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(insertedJsonObject, response.getBody());
	}
	
	@Test
	public void findByIdTestWithObjectNotFound() throws InvalidJsonObjectException {
		String tableName = "table";
		String jsonObjectId = new ObjectId().toHexString();
		
		ResponseEntity<String> response = get("/"+tableName+"/"+jsonObjectId);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}
	
	@Test
	public void postTest() throws InvalidJsonObjectException, NotFoundException {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		
		ResponseEntity<String> response = post("/"+tableName,jsonObject);
		String insertedJsonObjectId = Document.parse(response.getBody()).get("_id").toString();
		String foundObject = baasRepository.findById(tableName, insertedJsonObjectId);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(response.getBody(), foundObject);
	}
	
	@Test
	public void removeTest() throws InvalidJsonObjectException {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String insertedJsonObjectId = Document.parse(insertedJsonObject).get("_id").toString();
		ResponseEntity<String> response = delete("/"+tableName+"/"+insertedJsonObjectId);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void removeTestWithObjectNotFound() throws InvalidJsonObjectException {
		String tableName = "table";
		String jsonObjectId = new ObjectId().toHexString();
		
		ResponseEntity<String> response = delete("/"+tableName+"/"+jsonObjectId);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void updateTest() throws InvalidJsonObjectException {
		String tableName = "table";
		String jsonObject = new Document().append("name", "João").toJson();
		
		String insertedJsonObject = baasRepository.insert(tableName, jsonObject);
		String objectToUpdate = Document.parse(insertedJsonObject).append("name", "Maria").toJson();
		String objectToUpdateId = Document.parse(objectToUpdate).get("_id").toString();
		
		ResponseEntity<String> response = update("/"+tableName+"/"+objectToUpdateId, objectToUpdate);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void updateTestWithObjectNotFound() {
		String tableName = "table";
		String jsonObjectId = new ObjectId().toHexString();
		String objectToUpdate = new Document().append("_id", jsonObjectId).append("name", "joão").toJson();
		String objectToUpdateId = Document.parse(objectToUpdate).get("_id").toString();
		ResponseEntity<String> response = update("/"+tableName+"/"+objectToUpdateId, objectToUpdate);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void listTest() throws InvalidJsonObjectException {
		String tableName = "table";
		String jsonObjectOne = new Document().append("name", "joão").toJson();
		String jsonObjectTwo = new Document().append("name", "Tereza").toJson();
		
		baasRepository.insert(tableName, jsonObjectOne);
		baasRepository.insert(tableName, jsonObjectTwo);	
		ResponseEntity<String> response = get("/"+tableName);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void listTestWithEmptyCollection() throws InvalidJsonObjectException {
		String tableName = "table";

		ResponseEntity<String> response = get("/"+tableName);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
