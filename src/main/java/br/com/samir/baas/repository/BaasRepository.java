package br.com.samir.baas.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import br.com.samir.baas.database.Database;
import br.com.samir.baas.exception.InvalidJsonObjectException;
import br.com.samir.baas.exception.NonUniqueResultException;
import br.com.samir.baas.exception.NotFoundException;

@Repository
public class BaasRepository {

	@Autowired
	private Database database;

	public String insert(String tableName, String jsonObject) throws InvalidJsonObjectException {
		MongoCollection<Document> collection = database.getDatabase().getCollection(tableName);
		Document document = parseJsonToDocument(jsonObject);
		collection.insertOne(document);
		return document.toJson();
	}
	
	public void update(String tableName, String jsonObject, String id) throws InvalidJsonObjectException, NotFoundException {
		MongoCollection<Document> collection = database.getDatabase().getCollection(tableName);
		Document newDocument = parseJsonToDocument(jsonObject);
		newDocument.append("_id", new ObjectId(id));
		Bson objectId = createBsonObjectWithId(id);
		Long modifiedCount = collection.replaceOne(objectId, newDocument).getModifiedCount();
		if(modifiedCount == 0) {
			throw new NotFoundException();
		}
	}

	public String findById(String tableName, String id) throws NotFoundException {
		Bson objectId = createBsonObjectWithId(id);
		MongoCursor<Document> iterator = database.getDatabase().getCollection(tableName).find(objectId).iterator();
		return createSingleJsonElement(iterator);
	}

	public void remove(String tableName, String id) throws NotFoundException {
		Bson objectId = createBsonObjectWithId(id);
		Long deletedCount = database.getDatabase().getCollection(tableName)
				.deleteOne(objectId).getDeletedCount();
		if(deletedCount == 0) {
			throw new NotFoundException();
		}
	}
	
	private Bson createBsonObjectWithId(String id) throws NotFoundException {
		if(StringUtils.isBlank(id)) {
			throw new NotFoundException();
		}
		return new Document().append("_id", new ObjectId(id));
	}
	
	private Document parseJsonToDocument(String jsonObject) throws InvalidJsonObjectException {
		try {
			return Document.parse(jsonObject);
		} catch (BsonInvalidOperationException e) {
			throw new InvalidJsonObjectException();
		}
	}

	private List<String> createListjsonObjects(MongoCursor<Document> iterator) {
		List<String> jsonObjects = new ArrayList<>();
		while (iterator.hasNext()) {
			jsonObjects.add(iterator.next().toJson());
		}
		return jsonObjects;
	}

	private String createSingleJsonElement(MongoCursor<Document> iterator) {
		List<String> jsonObjects = createListjsonObjects(iterator);
		if (jsonObjects.size() > 1) {
			throw new NonUniqueResultException();
		}
		return jsonObjects.isEmpty() ? null : jsonObjects.get(0);
	}
}
