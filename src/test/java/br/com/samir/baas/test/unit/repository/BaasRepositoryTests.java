package br.com.samir.baas.test.unit.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import br.com.samir.baas.database.Database;
import br.com.samir.baas.exception.InvalidJsonObjectException;
import br.com.samir.baas.exception.NonUniqueResultException;
import br.com.samir.baas.repository.BaasRepository;

@RunWith(MockitoJUnitRunner.class)
public class BaasRepositoryTests {

	@InjectMocks
	BaasRepository baasRepository;

	@Mock
	private Database database;

	@Mock
	private MongoDatabase mongoDatabase;

	@Mock
	private MongoCollection<Document> mongoCollection;

	@Mock
	private FindIterable<Document> documents;

	@Mock
	private MongoCursor<Document> iterator;

	@Mock
	private Document documentValue;

	@Test(expected = InvalidJsonObjectException.class)
	public void insertTestWithInvalidJsonOject() {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();
		String invalidJsonObject = jsonObject.substring(2);

		when(database.getDatabase()).thenReturn(mongoDatabase);
		when(mongoDatabase.getCollection(tableName)).thenReturn(mongoCollection);

		baasRepository.insert(tableName, invalidJsonObject);
	}

	@Test
	public void insertTestWithValidJsonOject() {
		String tableName = "table";
		String jsonObject = new Document().append("name", "joão").toJson();

		when(database.getDatabase()).thenReturn(mongoDatabase);
		when(mongoDatabase.getCollection(tableName)).thenReturn(mongoCollection);

		String insertedObject = baasRepository.insert(tableName, jsonObject);
		assertEquals(jsonObject, insertedObject);
	}

	@Test(expected = NonUniqueResultException.class)
	public void findByIdTestWithQueryReturningTwoResults() {
		String tableName = "table";
		String id = "1";

		when(database.getDatabase()).thenReturn(mongoDatabase);
		when(mongoDatabase.getCollection(tableName)).thenReturn(mongoCollection);
		when(mongoCollection.find(Mockito.any(Bson.class))).thenReturn(documents);
		when(documents.iterator()).thenReturn(iterator);
		when(iterator.next()).thenReturn(documentValue);
		when(iterator.hasNext()).thenReturn(true, true, false);
		when(documentValue.toJson()).thenReturn("{}");

		baasRepository.findById(tableName, id);
	}

	@Test
	public void findByIdTestWithQueryReturningOneResult() {
		String tableName = "table";
		String id = "1";
		String expectedValue = "{}";

		when(database.getDatabase()).thenReturn(mongoDatabase);
		when(mongoDatabase.getCollection(tableName)).thenReturn(mongoCollection);
		when(mongoCollection.find(Mockito.any(Bson.class))).thenReturn(documents);
		when(documents.iterator()).thenReturn(iterator);
		when(iterator.next()).thenReturn(documentValue);
		when(iterator.hasNext()).thenReturn(true, false);
		when(documentValue.toJson()).thenReturn(expectedValue);

		String result = baasRepository.findById(tableName, id);
		assertEquals(expectedValue, result);
	}

	@Test
	public void findByIdTestWithQueryReturningEmpty() {
		String tableName = "table";
		String id = "1";

		when(database.getDatabase()).thenReturn(mongoDatabase);
		when(mongoDatabase.getCollection(tableName)).thenReturn(mongoCollection);
		when(mongoCollection.find(Mockito.any(Bson.class))).thenReturn(documents);
		when(documents.iterator()).thenReturn(iterator);
		when(iterator.next()).thenReturn(documentValue);
		when(iterator.hasNext()).thenReturn(false);

		String result = baasRepository.findById(tableName, id);
		assertNull(result);
	}
}
