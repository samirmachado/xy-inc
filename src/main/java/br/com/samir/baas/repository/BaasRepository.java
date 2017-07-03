package br.com.samir.baas.repository;

import java.util.ArrayList;
import java.util.List;

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

	public String findById(String tableName, String id) {
		Bson bsonObject = new Document().append("_id", new ObjectId(id));
		MongoCursor<Document> iterator = database.getDatabase().getCollection(tableName)
				.find(bsonObject ).iterator();
		return createSingleJsonElement(iterator);
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
